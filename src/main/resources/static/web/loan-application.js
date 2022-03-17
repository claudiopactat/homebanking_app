var app = new Vue({
    el: '#app',
    data: {
        client: {},
        accounts: [],
        loans: [],
        accountActive: [],

        rolAdmin: false,

        personal: false,
        hipotecario: false,
        automotriz: false,
        admin: false,

        cuotasPersonal: [],
        cuotasHipotecario: [],
        cuotasAutomotriz: [],
        cuotasAdmin: [],

        loanPersonal: 0,
        loanHipotecario: 0,
        loanAutomotriz: 0,
        loanAdmin: 0,

        montoSolicitado: 0,
        cantCuotas: null,

        montoFinalCuotasPersonal: 0,
        montoFinalCuotasHipotecario: 0,
        montoFinalCuotasAutomotriz: 0,
        montoFinalCuotasAdmin: 0,

        interesPersonal: 0,
        interesHipotecario: 0,
        interesAutomotriz: 0,
        interesAdmin: 0,

        cuentaDestino: "",
        nombrePrestamo: "",
    },

    created(){
        this.loadData()
        this.loadLoans()
    },

    methods: {
        loadData(){
            axios.get('/api/clients/current')

            .then(response => {
                // verificamos si el usuario que ingreso es admin.. si es asi lo habilitaremos a un tipo de prestamo especial
                if(response.data.email.includes("@mindhub.com")){
                    this.rolAdmin = true;
                }
                
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

        
        loadLoans(){
            axios.get('/api/loans')

            .then(response =>{
                //guardamos las cuotas de cada tipo de prestamo
                this.cuotasHipotecario = response.data[0].payments;
                this.cuotasPersonal = response.data[1].payments;
                this.cuotasAutomotriz = response.data[2].payments;
                this.cuotasAdmin = response.data[3].payments;
                
                this.interesHipotecario = response.data[0].interes;
                this.interesPersonal = response.data[1].interes;
                this.interesAutomotriz = response.data[2].interes;
                this.interesAdmin = response.data[3].interes;

                //guardamos los intereses de cada tipo de prestamo
                this.loanHipotecario = (response.data[0].interes / 100) + 1;
                this.loanPersonal = (response.data[1].interes / 100) + 1;
                this.loanAutomotriz = (response.data[2].interes / 100) + 1;
                this.loanAdmin = (response.data[3].interes / 100) + 1;
            })
        },
        
        prestamoPersonal(){
            this.personal = true;
            this.hipotecario = false;
            this.automotriz = false;
            this.admin = false;
        },

        prestamoHipotecario(){
            this.personal = false;
            this.hipotecario = true;
            this.automotriz = false;
            this.admin = false;
        },

        prestamoAutomotriz(){
            this.personal = false;
            this.hipotecario = false;
            this.automotriz = true;
            this.admin = false;
        },

        prestamoAdmin(){
            this.personal = false;
            this.hipotecario = false;
            this.automotriz = false;
            this.admin = true;
        },

        calcularMontoCuotas(){
            //definimos el monto final de cada cuota
            this.montoFinalCuotasPersonal = ((this.montoSolicitado * this.loanPersonal) / this.cantCuotas).toFixed(2);
            this.montoFinalCuotasHipotecario = ((this.montoSolicitado * this.loanHipotecario) / this.cantCuotas).toFixed(2);
            this.montoFinalCuotasAutomotriz = ((this.montoSolicitado * this.loanAutomotriz) / this.cantCuotas).toFixed(2);
            this.montoFinalCuotasAdmin = (this.montoSolicitado * this.loanAdmin / this.cantCuotas).toFixed(2);
        },

        verificarPrestamo(){
            if(this.cantCuotas == null || this.montoSolicitado <= 0 || this.cuentaDestino == ""){
                Swal.fire({
                    icon: 'warning',
                    title: 'Ha ocurrido un error!',
                    text: 'Verifica que llenaste bien todos los campos.. recuerda que el monto no puede ser cero ni negativo.',
                })    
            }else{
                this.realizarPrestamo();
            }
        },

        realizarPrestamo(){
            Swal.fire({
                title: '¿Esta seguro de realizar el prestamo?',
                text: "¡No podras revertir este proceso!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '¡Confirmar!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/loans',
                        {"name": this.nombrePrestamo, "number": this.cuentaDestino, "amount": this.montoSolicitado, "payments": this.cantCuotas}
                    )

                    Swal.fire(
                        '¡Exito!',
                        'Su prestamo a sido realizada con exito.',
                        'success'
                    )
                }
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