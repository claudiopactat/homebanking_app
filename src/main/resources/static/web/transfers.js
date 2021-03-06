var app = new Vue({
    el: '#app',
    data: {
        client: {},
        accounts: [],
        accountActive: [],

        amount: 0,
        accountOrigin: "",
        accountDestiny: "",
        description: "",

        cuentaPropia: false,
        cuentaTercero: false,
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

        formCuentaPropia(){
            this.cuentaPropia = true;
            this.cuentaTercero = false;
        },

        formCuentaTercero(){
            this.cuentaTercero = true;
            this.cuentaPropia = false;
        },

        verificarTransferencia(){
            if(this.accountOrigin == this.accountDestiny || this. amount <= 0){
                Swal.fire({
                    icon: 'warning',
                    title: 'Ha ocurrido un error!',
                    text: 'Verifica que llenaste bien todos los campos.. recuerda que la cuenta de destino no puede ser igual a la de origen y que el monto no puede ser cero ni negativo.',
                })    
            }else{
                this.realizarTransferencia();
            }
        },

        realizarTransferencia(){
            Swal.fire({
                title: '¿Esta seguro de realizar la transferencia?',
                text: "¡No podras revertir este proceso!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '¡Confirmar!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/transactions',
                        `amount=${this.amount}&description=${this.accountOrigin}+${this.description}&numberOrigin=${this.accountOrigin}&numberDestiny=${this.accountDestiny}`,
                        {headers:{'content-type':'application/x-www-form-urlencoded'}
                    })

                    Swal.fire(
                        '¡Exito!',
                        'Su transferencia a sido realizada con exito.',
                        'success'
                    )
                }
            })
        },
    },
})