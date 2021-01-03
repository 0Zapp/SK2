<template>
	<div class="container">
		<header class="jumbotron">
			<h3>Cards</h3>
		</header>
		<div>
			<div
				class="myCard"
				v-for="(card, index) in cards"
				v-bind:key="index"
			>
				<p>Card ID: {{ card.cardId }}</p>
				<p>Card Number: {{ card.cardNumber }}</p>
                <button @click="getSelection(card.cardId)" v-if="isSelect">Select</button>
			</div>
		</div>
		<div>
            <label>Card number: </label>
			<input type="number" v-model="newCard.cardNumber" />
            <br>
            <label>Security number: </label>
			<input type="number" v-model="newCard.securityNumber" />
            <br>
			<button @click="addCard">Add card</button>
		</div>
	</div>
</template>
<script>
import cardService from '../services/card.service';

export default {
    name: 'Card',
    props: ['isSelect', 'getSelection'],
	data() {
		return {
			cards: [],
			newCard: {
				cardNumber: null,
				securityNumber: null,
			},
		};
	},
	computed: {
		currentUser() {
			return this.$store.state.auth.user;
		},
	},
	mounted() {
		if (!this.currentUser) {
			this.$router.push('/login');
		} else {
			this.loadCards();
		}
	},
	methods: {
		loadCards() {
			cardService.getCards().then((response) => {
				console.log(response.data);
				this.cards = [...response.data];
			});
		},
		addCard() {
			cardService.addCard(this.newCard).then((response) => {
				console.log(response);
				this.loadCards();
			});
		},
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