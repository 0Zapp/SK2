<template>
  <div class="container">
    <header class="jumbotron">
      <h3>
         Profile
      </h3>
    </header>
    <div>
        <label>Name</label>
        <input type="text" v-model="user.name">
        <br>
        <label>Surname</label>
        <input type="text" v-model="user.surname">
        <br>
        <label>Email</label>
        <input type="email" v-model="user.email">
        <br>
        <label>Passport Number</label>
        <input type="number" v-model="user.passportNumber">
        <p type="text">Miles {{ user.miles }}</p>
        <p type="text">Rank {{ user.rank }}</p>
        <button @click="save">Save changes</button>
        <br>
        <label>Password</label>
        <input type="password" v-model="password">
        <button @click="savePassword">Save password</button>
    </div>

    <card/>

    <header class = "jumbotron">
        <h3>Tickets</h3>
    </header>
    <div>
        <div class="myCard" v-for="(ticket, index) in tickets" v-bind:key="index">
            <p>Flight ID: {{ticket.flightId}}</p>
            <p>Card ID: {{ticket.cardId}}</p>
            <p>Date: {{ticket.date}}</p>
            <p>Price: {{ticket.price}}</p>
            <p>Status: {{ticket.status}}</p>
        </div>
    </div>
  </div>
</template>

<script>
import authService from '../services/auth.service';
import ticketService from '../services/ticket.service';
import Card from './Card'

export default {
  name: 'Profile',
  components: {
      Card
    },
  data() {
      return {
          user: {},
          tickets: [],
          password: '',
          
      }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    } else {
        authService.getUser().then(response => {
            delete response.data.password 
            this.user = {...response.data}
        })
        this.loadTickets()
    }
  },
  methods: {
      loadTickets() {
          ticketService.getTickets().then(response => {
              console.log(response.data)
              this.tickets = [...response.data]
          })
      },   
      save() {
          authService.updateUser(this.user).then(response => {
              console.log(response)
          })
      },
      savePassword() {
          this.user.password = this.password
          authService.updateUser(this.user).then(response => {
              console.log(response)
          })
      }
  },
};
</script>
<style scoped>
    label {
        margin-right: 10px;
    }
    .myCard {
        border: 1px solid red;
        margin: 5px;
        padding: 10px;
    }
</style>