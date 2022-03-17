var app = new Vue({
    el: '#app',
    data: {
        client: {},
        accounts: [],
        cards: [],
        cardsInactive: [],
        accountActive: [],
        cardsCredit: [],
        cardsDebit: [],

        check: ["CREDIT","DEBIT"],
        date: new Date().toISOString(),
    },

    created(){
        this.loadData()
    },

    methods: {

        loadData(){
            axios.get('/api/clients/current')

            .then(response => {
                console.log(response)
                
                this.client = response.data;
                console.log(this.client.cards)
                this.accounts = this.client.account;
        
                // filtramos todas las tarjetas activas
                this.client.cards.filter(card =>{
                    if (this.check.includes(card.type) && card.active == true) {
                        return this.cards.push(card)
                    }
                })

                // filtramos las cuentas activas
                this.accounts.filter(account =>{
                    if(account.active == true){
                        return this.accountActive.push(account)
                    }
                })

                // filtramos las tarjetas activas separando Credito de Debito
                this.client.cards.filter(card =>{
                    if (card.type == 'CREDIT' && card.active == true) {
                        return this.cardsCredit.push(card)
                    }
                })
                this.client.cards.filter(card =>{
                    if (card.type == 'DEBIT' && card.active == true) {
                        return this.cardsDebit.push(card)
                    }
                })

                // ordenamos
                this.accountActive.sort((a,b) => a.id - b.id);
                this.accounts.sort((a,b) => a.id - b.id);
                this.client.loans.sort((a,b) => a.id - b.id);
                this.client.cards.sort((a,b) => a.id - b.id);
                this.cardsCredit.sort((a,b) => a.id - b.id);
                this.cardsDebit.sort((a,b) => a.id - b.id);
            })
        },

        signOut(){
            axios.post('/api/logout')

            .then(response => 
                Swal.fire({
                    icon: 'success',
                    title: 'Correcto',
                    text: 'Sesion cerrada correctamente!!',
                }),
            )
            
            .then(response => 
                console.log('signed out!!!')
            )
        },


        eliminaTarjeta(id){
            axios.patch('/api/clients/current/cards/delete/' + id)

            .then(response => 
                Swal.fire({
                    icon: 'success',
                    title: 'Correcto',
                    text: 'Tarjeta eliminada correctamente!!',
                }),
            )
            
            .then(response =>{
                return window.location.reload()
            })

            .then(response => 
                console.log('signed out!!!')
            )
        },

        mantenimiento(){
            Swal.fire({
                icon: 'warning',
                title: 'Ha ocurrido un error',
                text: 'Momentaneamente no funciona esta accion, intentelo mas tarde..',
            })
        }
    }
})