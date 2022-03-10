var app = new Vue({
    el: '#app',
    data: {
        firstName:"",
        lastName:"",
        email:"",
        password: "",

        iniciosesion: true,
        registro: false,
    },

    methods: {

        signIn(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`, {
                headers:{'content-type':'application/x-www-form-urlencoded'}
            })
            
            .then(response => {
                if(this.email.includes("@") && this.password != ""){
                    Swal.fire({
                        icon: 'success',
                        title: 'Correcto',
                        text: 'Bienvenido!!',
                        showConfirmButton: false,
                    })
                }
                setTimeout(() => {
                    window.location.href = 'http://localhost:8080/web/accounts.html'
                }, 2000);
            })

            .catch(error => 
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'El usuario ingresado no es correcto!',
                })
            )
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

        registerNewClient(){
            axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,{
                headers:{'content-type':'application/x-www-form-urlencoded'}
            })

            
            .then(response => {
                console.log('registered')
                if(this.firstName != "" && this.lastName != "" && this.email.includes("@") && this.password != ""){
                    this.signIn()
                }
            })

            .catch(error =>{
                console.log("error")
            })
        },

        mostrarFormulario(){
            if(this.iniciosesion){
                this.iniciosesion = false;
            }else{
                this.iniciosesion = true;
            }

            if(this.registro){
                this.registro = false;
            }else{
                this.registro = true;
            }
        }
    },
})