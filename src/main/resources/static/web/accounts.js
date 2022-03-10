var app = new Vue({
    el: '#app',
    data: {
        client: {},
        accounts: [],
        accountActive: [],

        type: "",
    },

    created(){
        this.loadData()
    },

    methods: {

        loadData(){
            axios.get('http://localhost:8080/api/clients/current')
            
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

        newAccount(tipo){
            axios.post('http://localhost:8080/api/clients/current/accounts',`accountType=${tipo}`)
            
            .then(response =>{
                window.location.href = 'http://localhost:8080/web/accounts.html'
            })
        },

        deleteAccount(id){
            axios.patch(`http://localhost:8080/api/clients/current/accounts/delete/${id}`)

            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: 'Correcto',
                    text: 'La cuenta se elimino con exito!',
                    showConfirmButton: true,
                })
                .then(response =>{
                    window.location.reload()
                })
            })

            .catch(error => 
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'No puedes eliminar una cuenta con saldo!',
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
        
    }
})