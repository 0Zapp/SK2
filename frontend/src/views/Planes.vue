<template>
	<div class="container">
		<header class="jumbotron">
			<h3>Planes</h3>
		</header>
		<div>
			<label>Name</label>
			<input type="text" v-model="newPlane.name" />
			<label>Capacity</label>
			<input type="number" v-model="newPlane.capacity" />
			<button @click="addPlane">Add Plane</button>
		</div>
        <div>
            <div class="plane" v-for="(plane, index) in planes" v-bind:key="index">
                <p>Name: {{ plane.name }}</p>
                <p>Capacity: {{ plane.capacity }}</p>
                <button @click="deletePlane(plane.planeId)">Delete</button>
            </div>
        </div>
	</div>
</template>

<script>
import planeService from '../services/plane.service';

export default {
	name: 'Planes',
	data() {
		return {
			planes: [],
			newPlane: {
                name: '',
                capacity: 0
            }
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
			this.showPlanes();
		}
	},
	methods: {
		showPlanes() {
            this.method = 'show'
			planeService.get().then((response) => {
				console.log(response.data);
				this.planes = [...response.data]
			});
        },
        deletePlane(planeId) {
            planeService.delete(planeId).then( () => {
                this.showPlanes()
            })
        },
        addPlane() {
            planeService.add(this.newPlane).then( () => {
                this.showPlanes()
            })
        }
	},
};
</script>
<style scoped>
label {
        margin: 10px;
    }
.plane {
    border: 1px solid red;
    margin: 5px;
    padding: 10px;
}
</style>