<template>
  <div class="student-list-container">
    <h1>Liste des étudiants</h1>

    <div class="action-bar">
      <div class="search-container">
        <input
            v-model="searchStudentId"
            type="number"
            placeholder="Rechercher par numéro d'étudiant"
            class="search-input"
        />
        <button @click="searchStudent" class="btn btn-secondary">
          Rechercher
        </button>
      </div>
      <button @click="showAddStudentModal = true" class="btn btn-primary">
        Ajouter un étudiant
      </button>
    </div>

    <table class="data-table">
      <thead>
      <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Mail</th>
        <th>Date de naissance</th>
        <th>Formation</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="student in students" :key="student.studentNumber">
        <td>{{ student.studentNumber }}</td>
        <td>{{ student.lastName }}</td>
        <td>{{ student.firstName }}</td>
        <td>{{ student.mail }}</td>
        <td>{{ student.birthDate }}</td>
        <td>{{ student.academicYearId }}</td>
        <td>
          <button @click="editStudent(student)" class="btn btn-primary">
            Modifier
          </button>
          <button @click="deleteStudent(student.studentNumber)" class="btn btn-danger">
            Supprimer
          </button>
          <button @click="viewStudentDetails(student.studentNumber)" class="btn btn-info">
            Détails
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Modal d'ajout/modification d'étudiant -->
    <div v-if="showAddStudentModal || showEditStudentModal" class="modal">
      <div class="modal-content">
        <h2>{{ isEditing ? 'Modifier' : 'Ajouter' }} un étudiant</h2>
        <form @submit.prevent="isEditing ? updateStudent() : addStudent()">
          <div class="form-group">
            <label>Prénom</label>
            <input
                v-model="currentStudent.firstName"
                type="text"
                required
            >
          </div>
          <div class="form-group">
            <label>Nom</label>
            <input
                v-model="currentStudent.lastName"
                type="text"
                required
            >
          </div>
          <div class="form-group">
            <label>Email</label>
            <input
                v-model="currentStudent.mail"
                type="email"
                required
            >
          </div>
          <div v-if="!isEditing" class="form-group">
            <label>Mot de passe</label>
            <input
                v-model="currentStudent.password"
                type="password"
                required
            >
          </div>
          <div class="form-group">
            <label>Date de naissance</label>
            <input
                v-model="currentStudent.birthDate"
                type="date"
                required
            >
          </div>
          <div class="form-group">
            <label>Année académique</label>
            <select v-model="currentStudent.academicYearId">
              <option value="">Aucune</option>
              <option v-for="year in academicYears" :key="year.id" :value="year.id">
                {{ year.formationName }}
              </option>
            </select>
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

    <!-- Modal de détails d'étudiant -->
    <div v-if="showDetailsModal" class="modal">
      <div class="modal-content">
        <h2>Détails de l'étudiant</h2>
        <div v-if="studentDetails" class="student-details">
          <div class="student-info">
            <h3>Informations personnelles</h3>
            <p><strong>Numéro d'étudiant:</strong> {{ studentDetails.student.studentNumber }}</p>
            <p><strong>Nom:</strong> {{ studentDetails.student.lastName }}</p>
            <p><strong>Prénom:</strong> {{ studentDetails.student.firstName }}</p>
            <p><strong>Email:</strong> {{ studentDetails.student.mail }}</p>
            <p><strong>Date de naissance:</strong> {{ studentDetails.student.birthDate }}</p>
          </div>

          <div v-if="studentDetails.academicYear" class="academic-info">
            <h3>Formation</h3>
            <p><strong>Nom de la formation:</strong> {{ studentDetails.academicYear.formationName }}</p>
            <p><strong>Responsable:</strong> {{ studentDetails.academicYear.responsibleEmail }}</p>
            <p><strong>UE optionnelles requises:</strong> {{ studentDetails.academicYear.optionalTeachingUnitsCount }}</p>
          </div>

          <div class="enrollment-info">
            <h3>Inscriptions</h3>

            <div class="enrollment-section">
              <h4>Groupes</h4>
              <p v-if="!studentDetails.groups || studentDetails.groups.length === 0">Aucun groupe</p>
              <ul v-else>
                <li v-for="(groupId, index) in studentDetails.groups" :key="index">
                  Groupe #{{ groupId }}
                </li>
              </ul>
              <div class="enrollment-actions">
                <div class="form-group">
                  <select v-model="selectedGroupId">
                    <option value="">Sélectionner un groupe</option>
                    <option value="1">Groupe 1</option>
                    <option value="2">Groupe 2</option>
                  </select>
                </div>
                <button
                    @click="addToGroup"
                    class="btn btn-primary"
                    :disabled="!selectedGroupId">
                  Ajouter au groupe
                </button>
              </div>
            </div>

            <div class="enrollment-section">
              <h4>UEs</h4>
              <p v-if="!studentDetails.modules || studentDetails.modules.length === 0">Aucune UE</p>
              <ul v-else>
                <li v-for="(ueId, index) in studentDetails.modules" :key="index">
                  UE #{{ ueId }}
                </li>
              </ul>
              <div class="enrollment-actions">
                <div class="form-group">
                  <select v-model="selectedUeId">
                    <option value="">Sélectionner une UE</option>
                    <option value="1">UE 1</option>
                    <option value="2">UE 2</option>
                  </select>
                </div>
                <button
                    @click="addToUe"
                    class="btn btn-primary"
                    :disabled="!selectedUeId">
                  Ajouter à l'UE
                </button>
              </div>
            </div>
          </div>
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
import studentService from '@/services/studentService'
import AcademicYearService from '@/services/AcademicYearService'

export default {
  data() {
    return {
      students: [],
      academicYears: [],
      showAddStudentModal: false,
      showEditStudentModal: false,
      showDetailsModal: false,
      isEditing: false,
      studentDetails: null,
      selectedGroupId: '',
      selectedUeId: '',
      searchStudentId: '',
      currentStudent: this.getEmptyStudent()
    }
  },
  methods: {
    getEmptyStudent() {
      return {
        studentNumber: '',
        birthDate: '',
        firstName: '',
        lastName: '',
        mail: '',
        password: '',
        academicYearId: ''
      }
    },
    async fetchStudents() {
      try {
        this.students = await studentService.getAllStudents()
        console.log('Étudiants récupérés:', this.students)
      } catch (error) {
        console.error('Erreur lors de la récupération des étudiants', error)
        alert('Erreur lors de la récupération des étudiants')
      }
    },
    async fetchAcademicYears() {
      try {
        this.academicYears = await AcademicYearService.getAllYears()
      } catch (error) {
        console.error('Erreur lors de la récupération des années académiques', error)
      }
    },
    async viewStudentDetails(id) {
      try {
        this.studentDetails = await studentService.getStudentInfo(id)
        this.showDetailsModal = true
      } catch (error) {
        console.error(`Erreur lors de la récupération des détails de l'étudiant ${id}`, error)
        alert(`Erreur lors de la récupération des détails de l'étudiant`)
      }
    },
    async deleteStudent(id) {
      if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')) {
        try {
          await studentService.deleteStudent(id)
          this.students = this.students.filter(student => student.studentNumber !== id)
        } catch (error) {
          console.error('Erreur lors de la suppression de l\'étudiant', error)
          alert('Erreur lors de la suppression de l\'étudiant')
        }
      }
    },
    async addStudent() {
      try {
        const createdStudent = await studentService.createStudent(this.currentStudent)
        this.students.push(createdStudent)
        this.closeModal()
      } catch (error) {
        console.error('Erreur lors de l\'ajout de l\'étudiant', error)
        alert('Erreur lors de l\'ajout de l\'étudiant')
      }
    },
    editStudent(student) {
      this.isEditing = true
      this.currentStudent = { ...student }
      this.showEditStudentModal = true
    },
    async updateStudent() {
      try {
        const updatedStudent = await studentService.updateStudent(
            this.currentStudent.studentNumber,
            this.currentStudent
        )
        const index = this.students.findIndex(student => student.studentNumber === updatedStudent.studentNumber)
        if (index !== -1) {
          this.students.splice(index, 1, updatedStudent)
        }
        this.closeModal()
      } catch (error) {
        console.error('Erreur lors de la mise à jour de l\'étudiant', error)
        alert('Erreur lors de la mise à jour de l\'étudiant')
      }
    },
    async addToGroup() {
      if (!this.selectedGroupId || !this.studentDetails?.student?.studentNumber) return

      try {
        await studentService.addStudentToGroup(
            this.studentDetails.student.studentNumber,
            this.selectedGroupId
        )

        // Rafraîchir les données
        const updatedDetails = await studentService.getStudentInfo(this.studentDetails.student.studentNumber)
        this.studentDetails = updatedDetails
        this.selectedGroupId = ''
      } catch (error) {
        console.error('Erreur lors de l\'ajout au groupe', error)
        alert('Erreur lors de l\'ajout au groupe')
      }
    },
    async addToUe() {
      if (!this.selectedUeId || !this.studentDetails?.student?.studentNumber) return

      try {
        await studentService.addStudentToUE(
            this.studentDetails.student.studentNumber,
            this.selectedUeId
        )

        // Rafraîchir les données
        const updatedDetails = await studentService.getStudentInfo(this.studentDetails.student.studentNumber)
        this.studentDetails = updatedDetails
        this.selectedUeId = ''
      } catch (error) {
        console.error('Erreur lors de l\'ajout à l\'UE', error)
        alert('Erreur lors de l\'ajout à l\'UE')
      }
    },
    closeModal() {
      this.showAddStudentModal = false
      this.showEditStudentModal = false
      this.isEditing = false
      this.currentStudent = this.getEmptyStudent()
    },
    async searchStudent() {
      if (!this.searchStudentId) {
        // Si le champ est vide, afficher tous les étudiants
        this.fetchStudents()
        return
      }

      try {
        const studentId = parseInt(this.searchStudentId)
        const student = await studentService.getStudentById(studentId)

        if (student) {
          // Afficher uniquement l'étudiant trouvé
          this.students = [student]
        } else {
          alert('Aucun étudiant trouvé avec ce numéro')
          this.students = []
        }
      } catch (error) {
        console.error('Erreur lors de la recherche de l\'étudiant', error)
        alert('Aucun étudiant trouvé avec ce numéro')
        this.students = []
      }
    }
  },
  mounted() {
    this.fetchStudents()
    this.fetchAcademicYears()
  }
}
</script>

<style scoped>
.student-list-container {
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
  width: 200px;
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
  width: 600px;
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

.form-group input, .form-group select {
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

.student-details {
  margin-top: 10px;
}

.student-details h3 {
  margin-top: 20px;
  margin-bottom: 10px;
  color: #333;
  border-bottom: 1px solid #ddd;
  padding-bottom: 5px;
}

.student-details h4 {
  margin-top: 15px;
  margin-bottom: 8px;
  color: #555;
}

.student-details p {
  margin: 5px 0;
}

.student-details ul {
  margin: 5px 0;
  padding-left: 20px;
}

.enrollment-section {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 4px;
}

.enrollment-actions {
  margin-top: 10px;
  display: flex;
  align-items: flex-end;
  gap: 10px;
}

.enrollment-actions .form-group {
  flex: 1;
  margin-bottom: 0;
}

.student-info, .academic-info, .enrollment-info {
  margin-bottom: 20px;
}
</style>