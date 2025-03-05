<template>
  <div class="academic-year-details">
    <div v-if="loading" class="loading">
      Loading academic year details...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    <div v-else-if="!academicYear" class="not-found">
      Academic year not found.
    </div>
    <div v-else class="details-container">
      <div class="details-header">
        <h2>{{ academicYear.formationName }}</h2>
        <div class="header-actions">
          <router-link :to="`/academic-years/${academicYear.id}/edit`" class="edit-button">
            Edit Academic Year
          </router-link>
        </div>
      </div>

      <div class="card">
        <h3>Academic Year Information</h3>
        <div class="info-row">
          <div class="info-label">ID:</div>
          <div class="info-value">{{ academicYear.id }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Formation Name:</div>
          <div class="info-value">{{ academicYear.formationName }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">TP Group Size:</div>
          <div class="info-value">{{ academicYear.tpGroupSize }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">TD Group Size:</div>
          <div class="info-value">{{ academicYear.tdGroupSize }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Optional Teaching Units:</div>
          <div class="info-value">{{ academicYear.optionalTeachingUnitsCount }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Responsible Email:</div>
          <div class="info-value">{{ academicYear.responsibleEmail }}</div>
        </div>
      </div>

      <div class="card" v-if="associatedStudents.length > 0">
        <h3>Enrolled Students</h3>
        <ul class="student-list">
          <li v-for="student in associatedStudents" :key="student.studentNumber" class="student-item">
            <router-link :to="`/students/${student.studentNumber}`">
              {{ student.firstName }} {{ student.lastName }}
            </router-link>
          </li>
        </ul>
      </div>
      <div class="card empty-students" v-else>
        <h3>Enrolled Students</h3>
        <p>No students are currently enrolled in this academic year.</p>
      </div>
    </div>
  </div>
</template>

<script>
import AcademicYearService from '@/services/AcademicYearService.js';
import StudentService from '@/services/StudentService.js';

export default {
  name: 'AcademicYearDetails',
  props: {
    id: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      academicYear: null,
      associatedStudents: [],
      loading: true,
      error: null
    };
  },
  created() {
    this.fetchAcademicYearDetails();
  },
  methods: {
    async fetchAcademicYearDetails() {
      this.loading = true;
      this.error = null;
      try {
        this.academicYear = await AcademicYearService.getAcademicYearById(this.id);
        await this.fetchAssociatedStudents();
      } catch (error) {
        this.error = 'Failed to load academic year details. Please try again later.';
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async fetchAssociatedStudents() {
      try {
        // For a real application, this would be an API endpoint to get students by academic year
        // For now, we'll fetch all students and filter
        const allStudents = await StudentService.getAllStudents();
        this.associatedStudents = allStudents.filter(
          student => student.academicYearId === parseInt(this.id)
        );
      } catch (error) {
        console.error('Failed to load associated students:', error);
      }
    }
  },
  watch: {
    id() {
      this.fetchAcademicYearDetails();
    }
  }
};
</script>

<style scoped>
.academic-year-details {
  margin-bottom: 30px;
}

.loading, .error, .not-found {
  text-align: center;
  padding: 20px;
  margin: 20px 0;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.error {
  color: #dc3545;
}

.details-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.details-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.edit-button {
  background-color: #ffc107;
  color: #212529;
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  font-weight: bold;
}

.card {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.info-row {
  display: flex;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e9ecef;
}

.info-row:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.info-label {
  font-weight: bold;
  width: 180px;
  color: #6c757d;
}

.info-value {
  flex: 1;
}

.student-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.student-item {
  padding: 10px 0;
  border-bottom: 1px solid #e9ecef;
}

.student-item:last-child {
  border-bottom: none;
}

.student-item a {
  color: #007bff;
  text-decoration: none;
}

.student-item a:hover {
  text-decoration: underline;
}

.empty-students p {
  color: #6c757d;
  font-style: italic;
}

@media (max-width: 768px) {
  .info-row {
    flex-direction: column;
  }
  
  .info-label {
    width: 100%;
    margin-bottom: 5px;
  }
}
</style>
