var app = new Vue({
    el: '#app',
    data: {
        documento: null,
        email: "",
        reclamo: "",

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
        
        enviarReclamo(){
            if(this.documento != null && this.email.includes("@") && this.reclamo != ""){
                console.log("asdsd")

                Swal.fire({
                    icon: 'success',
                    title: 'Correcto!',
                    text: 'Su reclamo fue enviado exitosamente!!',
                    showConfirmButton: true,
                });
            }
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