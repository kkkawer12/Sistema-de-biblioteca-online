document.addEventListener('DOMContentLoaded', function() {
    const cadastroForm = document.getElementById('cadastroForm');
    
    cadastroForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = new FormData(cadastroForm);
        
        // Validação de senha
        const senha = formData.get('senha');
        const confirmSenha = formData.get('confirmSenha');
        
        if (senha !== confirmSenha) {
            const errorDiv = document.getElementById('cadastroError');
            errorDiv.textContent = 'As senhas não coincidem!';
            errorDiv.style.display = 'block';
            return;
        }
        
        fetch('cadastro', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                // Exibe mensagem de sucesso e redireciona
                alert(data.message);
                window.location.href = data.redirect;
            } else {
                // Exibe mensagem de erro
                const errorDiv = document.getElementById('cadastroError');
                errorDiv.textContent = data.message;
                errorDiv.style.display = 'block';
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            const errorDiv = document.getElementById('cadastroError');
            errorDiv.textContent = 'Erro ao realizar cadastro. Tente novamente mais tarde.';
            errorDiv.style.display = 'block';
        });
    });
    
    // Validação em tempo real dos campos
    const inputs = cadastroForm.querySelectorAll('input[required]');
    inputs.forEach(input => {
        input.addEventListener('input', function() {
            if (this.validity.valid) {
                this.classList.remove('invalid');
                this.classList.add('valid');
            } else {
                this.classList.remove('valid');
                this.classList.add('invalid');
            }
        });
    });
}); 