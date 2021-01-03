<template>
	<div class="container">
		<header class="jumbotron">
			<h3>Flights</h3>
		</header>
		<div>
			<label>PlaneID</label>
			<input type="number" v-model="search.planeId" />
			<label>Starting Destination</label>
			<input type="text" v-model="search.startingDestination" />
			<label>Ending Destination</label>
			<input type="text" v-model="search.endingDestination" />
			<label>Duration</label>
			<input type="number" v-model="search.duration" />
			<label>Price</label>
			<input type="number" v-model="search.price" />
			<button @click="searchFlights">Search</button>
			<button @click="showFlights">Show All</button>
			<button @click="changePage(-1)">Previous</button>
			<button @click="changePage(1)">Next</button>
		</div>
        <div>
            <div class="flight" v-for="(flight, index) in flights" v-bind:key="index">
                <p>Plane ID: {{flight.planeID}}</p>
                <p>Starting Destination: {{flight.startingDestination}}</p>
                <p>Ending Destination: {{flight.endingDestination}}</p>
                <p>Length in miles: {{flight.duration}}</p>
                <p>Price in $: {{flight.price}}</p>
                <button v-if="isAdmin" @click="deleteFlight(flight.flightId)">Delete</button>
            </div>
        </div>
        <div v-if="isAdmin">
            <label>PlaneID</label>
			<input type="number" v-model="newFlight.planeID" />
			<label>Starting Destination</label>
			<input type="text" v-model="newFlight.startingDestination" />
			<label>Ending Destination</label>
			<input type="text" v-model="newFlight.endingDestination" />
			<label>Duration</label>
			<input type="number" v-model="newFlight.duration" />
			<label>Price</label>
			<input type="number" v-model="newFlight.price" />
			<button @click="addFlight">Add</button>
        </div>
	</div>
</template>

<script>
import flightService from '../services/flight.service';

export default {
	name: 'Flights',
	data() {
		return {
			flights: [],
			search: {
				planeID: 0,
				startingDestination: ' ',
				endingDestination: ' ',
				duration: 0,
				price: 0,
            },
            newFlight: {
				planeID: 0,
				startingDestination: ' ',
				endingDestination: ' ',
				duration: 0,
				price: 0,
            },
            size: 3,
            page: 0,
            method: 'show'
		};
	},
	computed: {
		currentUser() {
			return this.$store.state.auth.user;
        },
        isAdmin() {
            console.log(this.$store.state)
            return this.$store.state.auth.isAdmin == 1;
        }
	},
	mounted() {
		if (!this.currentUser) {
			this.$router.push('/login');
		} else {
			this.showFlights();
		}
	},
	methods: {
		showFlights() {
            this.method = 'show'
			flightService.show(this.page, this.size).then((response) => {
				console.log(response.data);
				this.flights = [...response.data.content]
			});
        },
        searchFlights() {
            this.method = 'search'
            flightService.search(this.page, this.size, this.search).then((response) => {
				console.log(response.data);
				this.flights = [...response.data.content]
			});
        },
        changePage(amount) {
            this.page += amount;
            if (this.method === 'show')
                this.showFlights()
            else
                this.searchFlights()
        },
        deleteFlight(flightId) {
            flightService.delete(flightId).then(response => {
                if (this.method === 'show')
                    this.showFlights()
                else
                    this.searchFlights()
            })
        },
        addFlight(){
            flightService.add(this.newFlight).then(response => {
                if (this.method === 'show')
                    this.showFlights()
                else
                    this.searchFlights()
            })
        }
	},
};
</script>
<style scoped>
label {
        margin: 10px;
    }
.flight {
    border: 1px solid red;
    margin: 5px;
    padding: 10px;
}
</style>