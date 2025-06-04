// Função para validar CPF
function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');

    if (cpf.length !== 11) return false;

    // Verifica se todos os dígitos são iguais
    if (/^(\d)\1{10}$/.test(cpf)) return false;

    // Validação dos dígitos verificadores
    let soma = 0;
    let resto;

    for (let i = 1; i <= 9; i++) {
        soma += parseInt(cpf.substring(i-1, i)) * (11 - i);
    }

    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.substring(9, 10))) return false;

    soma = 0;
    for (let i = 1; i <= 10; i++) {
        soma += parseInt(cpf.substring(i-1, i)) * (12 - i);
    }

    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.substring(10, 11))) return false;

    return true;
}

// Função para validar RG
function validarRG(rg) {
    rg = rg.replace(/[^\d]/g, '');
    return rg.length >= 7 && rg.length <= 9;
}

// Função para validar e-mail
function validarEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Função para validar RA
function validarRA(ra) {
    ra = ra.replace(/\D/g, '');
    return ra.length >= 5 && ra.length <= 12;
}

// Função para formatar CPF
function formatarCPF(cpf) {
    return cpf.replace(/\D/g, '')
              .replace(/(\d{3})(\d)/, '$1.$2')
              .replace(/(\d{3})(\d)/, '$1.$2')
              .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
}

// Função para formatar RG
function formatarRG(rg) {
    return rg.replace(/\D/g, '')
             .replace(/(\d{2})(\d)/, '$1.$2')
             .replace(/(\d{3})(\d)/, '$1.$2')
             .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
}

// Função para formatar RA
function formatarRA(ra) {
    return ra.replace(/\D/g, '')
             .replace(/(\d{2})(\d)/, '$1.$2')
             .replace(/(\d{3})(\d)/, '$1.$2');
}

// Configuração dos formulários
document.addEventListener('DOMContentLoaded', function() {
    // Máscara para CPF
    const cpfInputs = document.querySelectorAll('input[name="cpf"]');
    cpfInputs.forEach(input => {
        input.addEventListener('input', function() {
            this.value = formatarCPF(this.value);
        });
    });

    // Máscara para RG
    const rgInputs = document.querySelectorAll('input[name="rg"]');
    rgInputs.forEach(input => {
        input.addEventListener('input', function() {
            this.value = formatarRG(this.value);
        });
    });

    // Máscara para RA
    const raInput = document.querySelector('input[name="ra"]');
    if (raInput) {
        raInput.addEventListener('input', function() {
            this.value = formatarRA(this.value);
        });
    }

    // Validação do formulário de cadastro
    const formCadastro = document.querySelector('form[action="cadastro"]');
    if (formCadastro) {
        formCadastro.addEventListener('submit', function(e) {
            e.preventDefault();

            const nome = this.querySelector('#nome').value.trim();
            const cpf = this.querySelector('#cpf').value;
            const rg = this.querySelector('#rg').value;
            const email = this.querySelector('#email').value;
            const faculdade = this.querySelector('#faculdade').value;
            const ra = this.querySelector('#ra').value;
            const termos = this.querySelector('#termos').checked;

            let isValid = true;
            let mensagem = '';

            // Validação do nome
            if (nome.length < 3) {
                mensagem += 'Nome deve ter pelo menos 3 caracteres\n';
                isValid = false;
            }

            // Validação do CPF
            if (!validarCPF(cpf)) {
                mensagem += 'CPF inválido\n';
                isValid = false;
            }

            // Validação do RG
            if (!validarRG(rg)) {
                mensagem += 'RG inválido\n';
                isValid = false;
            }

            // Validação do e-mail
            if (!validarEmail(email)) {
                mensagem += 'E-mail inválido\n';
                isValid = false;
            }

            // Validação da faculdade
            if (!faculdade) {
                mensagem += 'Selecione uma faculdade\n';
                isValid = false;
            }

            // Validação do RA
            if (!validarRA(ra)) {
                mensagem += 'RA inválido\n';
                isValid = false;
            }

            // Validação dos termos
            if (!termos) {
                mensagem += 'Você deve aceitar os termos de uso\n';
                isValid = false;
            }

            if (!isValid) {
                alert(mensagem);
            } else {
                this.submit();
            }
        });
    }

    // Validação do formulário de login
    const formLogin = document.querySelector('form[action="login"]');
    if (formLogin) {
        formLogin.addEventListener('submit', function(e) {
            e.preventDefault();

            const cpf = this.querySelector('#cpf').value;
            const rg = this.querySelector('#rg').value;

            let isValid = true;
            let mensagem = '';

            // Validação do CPF
            if (!validarCPF(cpf)) {
                mensagem += 'CPF inválido\n';
                isValid = false;
            }

            // Validação do RG
            if (!validarRG(rg)) {
                mensagem += 'RG inválido\n';
                isValid = false;
            }

            if (!isValid) {
                alert(mensagem);
            } else {
                this.submit();
            }
        });
    }
}); 