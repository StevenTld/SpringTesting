<template>
  <div class="academic-year-container">
    <h1>Liste des Années Académiques</h1>

    <div class="action-bar">
      <div class="search-container">
        <input
            v-model="searchYearId"
            type="number"
            placeholder="Rechercher par ID"
            class="search-input"
        />
        <button @click="searchYear" class="btn btn-secondary">
          Rechercher
        </button>
      </div>
      <button @click="showAddAcademicYearModal = true" class="btn btn-primary">
        Ajouter une année académique
      </button>
    </div>

    <table class="data-table">
      <thead>
      <tr>
        <th>ID</th>
        <th>Formation</th>
        <th>Nombre d'UE</th>
        <th>Responsable</th>
        <th>Taille TD</th>
        <th>Taille TP</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="year in years" :key="year.id">
        <td>{{ year.id }}</td>
        <td>{{ year.formationName }}</td>
        <td>{{ year.optionalTeachingUnitsCount }}</td>
        <td>{{ year.responsibleEmail }}</td>
        <td>{{ year.tdGroupSize }}</td>
        <td>{{ year.tpGroupSize }}</td>
        <td>
          <button @click="editYear(year)" class="btn btn-primary">
            Modifier
          </button>
          <button @click="deleteYear(year.id)" class="btn btn-danger">
            Supprimer
          </button>
          <button @click="viewYearDetails(year.id)" class="btn btn-info">
            Détails
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Modal d'ajout/modification d'année académique -->
    <div v-if="showAddAcademicYearModal || showEditAcademicYearModal" class="modal">
      <div class="modal-content">
        <h2>{{ isEditing ? 'Modifier' : 'Ajouter' }} une Année Académique</h2>
        <form @submit.prevent="isEditing ? updateAcademicYear() : addAcademicYear()">
          <div class="form-group">
            <label>Nom de la formation</label>
            <input
                v-model="currentAcademicYear.formationName"
                type="text"
                required
            >
          </div>
          <div class="form-group">
            <label>Taille des groupes TP</label>
            <input
                v-model="currentAcademicYear.tpGroupSize"
                type="number"
                required
            >
          </div>
          <div class="form-group">
            <label>Taille des groupes TD</label>
            <input
                v-model="currentAcademicYear.tdGroupSize"
                type="number"
                required
            >
          </div>
          <div class="form-group">
            <label>Nombre d'UE optionnelles</label>
            <input
                v-model="currentAcademicYear.optionalTeachingUnitsCount"
                type="number"
                required
            >
          </div>
          <div class="form-group">
            <label>Email du responsable</label>
            <input
                v-model="currentAcademicYear.responsibleEmail"
                type="email"
                required
            >
          </div>
          <div class="modal-actions">
            <button
                type="button"
                @click="closeModal"
                class="btn btn-secondary"
            >
              Annuler
            </button>
            <button
                type="submit"
                class="btn btn-primary"
            >
              {{ isEditing ? 'Mettre à jour' : 'Ajouter' }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Modal de détails d'année académique -->
    <div v-if="showDetailsModal" class="modal">
      <div class="modal-content">
        <h2>Détails de l'Année Académique</h2>
        <div v-if="selectedYear" class="year-details">
          <p><strong>ID:</strong> {{ selectedYear.id }}</p>
          <p><strong>Formation:</strong> {{ selectedYear.formationName }}</p>
          <p><strong>Taille des groupes TP:</strong> {{ selectedYear.tpGroupSize }}</p>
          <p><strong>Taille des groupes TD:</strong> {{ selectedYear.tdGroupSize }}</p>
          <p><strong>Nombre d'UE optionnelles:</strong> {{ selectedYear.optionalTeachingUnitsCount }}</p>
          <p><strong>Responsable:</strong> {{ selectedYear.responsibleEmail }}</p>

          <h3>Groupes</h3>
          <p v-if="!selectedYear.groups || selectedYear.groups.length === 0">Aucun groupe associé</p>
          <ul v-else>
            <li v-for="(group, index) in selectedYear.groups" :key="index">
              {{ group }}
            </li>
          </ul>

          <h3>Unités d'enseignement</h3>
          <p v-if="!selectedYear.teachingUnits || selectedYear.teachingUnits.length === 0">Aucune unité d'enseignement associée</p>
          <ul v-else>
            <li v-for="(unit, index) in selectedYear.teachingUnits" :key="index">
              {{ unit }}
            </li>
          </ul>
        </div>
        <div class="modal-actions">
          <button
              type="button"
              @click="showDetailsModal = false"
              class="btn btn-secondary"
          >
            Fermer
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AcademicYearService from '@/services/AcademicYearService'

export default {
  data() {
    return {
      years: [],
      showAddAcademicYearModal: false,
      showEditAcademicYearModal: false,
      showDetailsModal: false,
      isEditing: false,
      selectedYear: null,
      searchYearId: '',
      currentAcademicYear: this.getEmptyAcademicYear()
    }
  },
  methods: {
    getEmptyAcademicYear() {
      return {
        formationName: '',
        tpGroupSize: null,
        tdGroupSize: null,
        optionalTeachingUnitsCount: null,
        responsibleEmail: '',
        groups: [],
        teachingUnits: []
      }
    },
    async fetchYears() {
      try {
        this.years = await AcademicYearService.getAllYears()
        console.log('Années académiques récupérées:', this.years)
      } catch (error) {
        console.error('Erreur lors de la récupération des années académiques', error)
        alert('Erreur lors de la récupération des années académiques')
      }
    },
    async viewYearDetails(id) {
      try {
        this.selectedYear = await AcademicYearService.getYearById(id)
        this.showDetailsModal = true
      } catch (error) {
        console.error(`Erreur lors de la récupération des détails de l'année ${id}`, error)
        alert(`Erreur lors de la récupération des détails de l'année`)
      }
    },
    async deleteYear(id) {
      if (confirm('Êtes-vous sûr de vouloir supprimer cette année académique ?')) {
        try {
          await AcademicYearService.deleteYear(id)
          this.years = this.years.filter(year => year.id !== id)
        } catch (error) {
          console.error('Erreur lors de la suppression de l\'année académique', error)
          alert('Erreur lors de la suppression de l\'année académique')
        }
      }
    },
    async addAcademicYear() {
      try {
        const createdYear = await AcademicYearService.createYear(this.currentAcademicYear)
        this.years.push(createdYear)
        this.closeModal()
      } catch (error) {
        console.error('Erreur lors de l\'ajout de l\'année académique', error)
        alert('Erreur lors de l\'ajout de l\'année académique')
      }
    },
    editYear(year) {
      this.isEditing = true
      this.currentAcademicYear = { ...year }
      this.showEditAcademicYearModal = true
    },
    async updateAcademicYear() {
      try {
        const updatedYear = await AcademicYearService.updateYear(this.currentAcademicYear)
        const index = this.years.findIndex(year => year.id === updatedYear.id)
        if (index !== -1) {
          this.years.splice(index, 1, updatedYear)
        }
        this.closeModal()
      } catch (error) {
        console.error('Erreur lors de la mise à jour de l\'année académique', error)
        alert('Erreur lors de la mise à jour de l\'année académique')
      }
    },
    closeModal() {
      this.showAddAcademicYearModal = false
      this.showEditAcademicYearModal = false
      this.isEditing = false
      this.currentAcademicYear = this.getEmptyAcademicYear()
    },
    async searchYear() {
      if (!this.searchYearId) {
        // Si le champ est vide, afficher toutes les années académiques
        this.fetchYears()
        return
      }

      try {
        const yearId = parseInt(this.searchYearId)
        const year = await AcademicYearService.getYearById(yearId)

        if (year) {
          // Afficher uniquement l'année académique trouvée
          this.years = [year]
        } else {
          alert('Aucune année académique trouvée avec cet ID')
          this.years = []
        }
      } catch (error) {
        console.error('Erreur lors de la recherche de l\'année académique', error)
        alert('Aucune année académique trouvée avec cet ID')
        this.years = []
      }
    }
  },
  mounted() {
    this.fetchYears()
  }
}
</script>

<style scoped>
.academic-year-container {
  padding: 20px;
}

.action-bar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-container {
  display: flex;
  gap: 10px;
  align-items: center;
}

.search-input {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 150px;
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

.btn-info {
  background-color: #2196F3;
  color: white;
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
  max-height: 80vh;
  overflow-y: auto;
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
  margin-top: 20px;
}

.year-details p {
  margin: 10px 0;
}

.year-details h3 {
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 1.1em;
}

.year-details ul {
  margin: 0;
  padding-left: 20px;
}
</style>