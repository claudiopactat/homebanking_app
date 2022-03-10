var app = new Vue({
    el: '#app',
    data: {
        accounts: [],

        cardType: "",
        cardColor: "",
    },

    methods: {

        newCard(){
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.cardColor}`, {
                headers:{'content-type':'application/x-www-form-urlencoded'}
            })
            
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: 'Correcto',
                    text: 'Bienvenido!!',
                })
            })
            
            .then(response => 
                window.location.href = 'http://localhost:8080/web/cards.html',
            )

            .catch(error => 
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'A ocurrido un error de permisos o bien ya excedio el limite de tarjetas!',
                })
            )
        },
    },
})