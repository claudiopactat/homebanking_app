var app = new Vue({
    el: '#app',
    data: {
        clients: [],
        json: [],
        url: "",

        firstName: "",
        lastName: "",
        email: ""

    },

    created(){
        this.loadData()
    },

    methods: {
        loadData(){

            axios.get('/rest/clients')

            .then(response => {
                this.clients = response.data._embedded.clients;
                this.json = response;
            })
        },

        addClient(){
            if(this.firstName != "" && this.lastName != "" && this.email.includes('@')){
                this.postClient()
                console.log("si entraaaa")
            }
            
        },

        postClient(){
            axios.post('/rest/clients', {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
            })

            .then(response => {
                this.loadData()
            })

        },

        deleteClient(x){
            this.url = x._links.client.href

            axios.delete(this.url)

            .then(deleted => {
                this.loadData()
            })

        }

        
    },

    

    

})