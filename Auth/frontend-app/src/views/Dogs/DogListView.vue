<template>
  <div class="dog-list-container">
    <h1>Liste des Chiens</h1>

    <div class="action-bar">
      <button @click="showAddDogModal = true" class="btn btn-primary">
        Ajouter un Chien
      </button>
    </div>

    <table class="data-table">
      <thead>
      <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Race</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="dog in dogs" :key="dog.id">
        <td>{{ dog.id }}</td>
        <td>{{ dog.name }}</td>
        <td>{{ dog.race }}</td>
        <td>
          <button @click="deleteDog(dog.id)" class="btn btn-danger">
            Supprimer
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Modal d'ajout de chien -->
    <div v-if="showAddDogModal" class="modal">
      <div class="modal-content">
        <h2>Ajouter un Chien</h2>
        <form @submit.prevent="addDog">
          <div class="form-group">
            <label>Nom</label>
            <input
                v-model="newDog.name"
                type="text"
                required
            >
          </div>
          <div class="form-group">
            <label>Race</label>
            <input
                v-model="newDog.race"
                type="text"
                required
            >
          </div>
          <div class="modal-actions">
            <button
                type="button"
                @click="showAddDogModal = false"
                class="btn btn-secondary"
            >
              Annuler
            </button>
            <button
                type="submit"
                class="btn btn-primary"
            >
              Ajouter
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import DogService from '@/services/dogService'

export default {
  data() {
    return {
      dogs: [],
      showAddDogModal: false,
      newDog: {
        name: '',
        race: ''
      }
    }
  },
  methods: {
    async fetchDogs() {
      try {
        this.dogs = await DogService.getAllDogs()
      } catch (error) {
        console.error('Erreur lors de la récupération des chiens', error)
      }
    },
    async deleteDog(id) {
      try {
        await DogService.deleteDog(id)
        this.dogs = this.dogs.filter(dog => dog.id !== id)
      } catch (error) {
        console.error('Erreur lors de la suppression du chien', error)
      }
    },
    async addDog() {
      try {
        const createdDog = await DogService.createDog(this.newDog)
        this.dogs.push(createdDog)
        this.showAddDogModal = false
        this.newDog = { name: '', race: '' }
      } catch (error) {
        console.error('Erreur lors de l\'ajout du chien', error)
      }
    }
  },
  mounted() {
    this.fetchDogs()
  }
}
</script>

<style scoped>
.dog-list-container {
  padding: 20px;
}

.action-bar {
  margin-bottom: 20px;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th,
.data-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.data-table thead {
  background-color: #f2f2f2;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
}

.btn-primary {
  background-color: #4CAF50;
  color: white;
}

.btn-danger {
  background-color: #f44336;
  color: white;
}

.btn-secondary {
  background-color: #e7e7e7;
  color: black;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 400px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>