var app = new Vue({
    el: '#app',
    data: {
        client: {},
        accounts: [],
        accountActive: [],

        cardType: "",
        cardColor: "",
    },

    created(){
        this.loadData()
    },

    methods: {

        loadData(){
            axios.get('/api/clients/current')
            
            .then(response => {
                
                this.client = response.data;
                this.accounts = this.client.account;
            
                this.accounts.filter(account =>{
                    if(account.active == true){
                        return this.accountActive.push(account)
                    }
                })

                // ordenamos
                this.accountActive.sort((a,b) => a.id - b.id);
                this.accounts.sort((a,b) => a.id - b.id);
                this.client.loans.sort((a,b) => a.id - b.id);
            })
        },

        newCard(){
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.cardColor}`, {
                headers:{'content-type':'application/x-www-form-urlencoded'}
            })
            
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: 'Correcto',
                    text: 'Tarjeta creada correctamente!!',
                })
                setTimeout(() => {
                    window.location.href = '/web/cards.html'
                }, 2000);
            })

            .catch(error => 
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'A ocurrido un error.. Puede que ya haya excedido el limite de tarjetas de ese tipo o bien verifique haber llenado correctamente todos los campos!',
                })
                
            )
        },

        mantenimiento(){
            Swal.fire({
                icon: 'warning',
                title: 'Ha ocurrido un error',
                text: 'Momentaneamente no funciona esta accion, intentelo mas tarde..',
            })
        }
    },
})


