var app = new Vue({
    el: '#app',
    data: {
        client: {},
        accounts: [],
        cards: [],
        cardsInactive: [],
        check: ["CREDIT","DEBIT"],
        date: new Date().toISOString(),
    },

    created(){
        this.loadData()
    },

    methods: {

        loadData(){
            axios.get('http://localhost:8080/api/clients/current')

            .then(response => {
                console.log(response)
                
                this.client = response.data;
                console.log(this.client.cards)
                this.accounts = this.client.account;
        
                this.client.cards.filter(card =>{
                    if (this.check.includes(card.type) && card.active == true) {
                        return this.cards.push(card)
                    }
                })

                // ordenamos
                this.accounts.sort((a,b) => a.id - b.id);
                this.client.loans.sort((a,b) => a.id - b.id);
                this.client.cards.sort((a,b) => a.id - b.id);
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
                    text: 'Sesion cerrada correctamente!!',
                }),
            )
            
            .then(response =>{
                return window.location.reload()
            })

            .then(response => 
                console.log('signed out!!!')
            )
        }
    }
})