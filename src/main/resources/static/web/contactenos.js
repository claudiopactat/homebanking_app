var app = new Vue({
    el: '#app',
    data: {
        accounts: [],
        accountActive: [],
    },

    created(){
        this.loadData()
    },

    methods: {

        loadData(){
            axios.get('/api/clients/current')
            
            .then(response => {
                
                this.accounts = response.data.account;
            
                this.accounts.filter(account =>{
                    if(account.active == true){
                        return this.accountActive.push(account)
                    }
                })

                // ordenamos
                this.accountActive.sort((a,b) => a.id - b.id);
                this.accounts.sort((a,b) => a.id - b.id);
            })
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