var app = new Vue({
    el: '#app',
    data: {
        account: {},
        accounts: [],
        transactions: [],
    },

    created(){
        this.loadData()
    },

    methods: {
        
        loadData(){
            const urlParams = new URLSearchParams(window.location.search);
            const myId = urlParams.get('id');

            axios.get('/api/clients/current')
            
            .then(response => { 
                
                this.client = response.data;
                this.accounts = this.client.account;
                
                this.account = this.accounts.find(account =>{
                    return account.id == myId;
                }) 

                this.transactions = this.account.transactions;
            
                // ordenamos 
                this.transactions.sort((a,b) => a.id - b.id);
                this.accounts.sort((a,b) => a.id - b.id);
                this.client.loans.sort((a,b) => a.id - b.id);
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
    }
})