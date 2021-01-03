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
    <header class = "jumbotron">
        <h3>Cards</h3>
    </header>
    <div>
        <div class="card" v-for="(card, index) in cards" v-bind:key="index">
            <p>{{ card.cardNumber}}</p>
        </div>
    </div>
    <div>
        <input type="number" v-model="newCard.cardNumber">
        <input type="number" v-model="newCard.securityNumber">
        <button @click="addCard">Add card</button>
    </div>
  </div>
</template>

<script>
import authService from '../services/auth.service';
import cardService from '../services/card.service';

export default {
  name: 'Profile',
  data() {
      return {
          user: {email: "abcd"},
          cards: [],
          password: '',
          newCard: {
              cardNumber: null,
              securityNumber: null
          }
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
        this.loadCards()
    }
  },
  methods: {
      loadCards() {
            cardService.getCards().then(response => {
            console.log(response.data)
            this.cards = [...response.data]
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
      },
      addCard() {
          cardService.addCard(this.newCard).then(response => {
              console.log(response)
              this.loadCards()
          })
      }
  },
};
</script>
<style lang="css">
    label {
        margin-right: 10px;
    }
    .card {
        border: 1px solid red;
        margin: 5px
    }
</style>