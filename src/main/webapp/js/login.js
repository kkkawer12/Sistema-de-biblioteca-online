document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = new FormData(loginForm);
        
        fetch('login', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                // Redireciona para a área do usuário em caso de sucesso
                window.location.href = data.redirect;
            } else {
                // Exibe mensagem de erro
                const errorDiv = document.getElementById('loginError');
                errorDiv.textContent = data.message;
                errorDiv.style.display = 'block';
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            const errorDiv = document.getElementById('loginError');
            errorDiv.textContent = 'Erro ao realizar login. Tente novamente mais tarde.';
            errorDiv.style.display = 'block';
        });
    });
}); 