<template>
  <div class="student-details">
    <div v-if="loading" class="loading">
      Loading student details...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    <div v-else-if="!studentInfo" class="not-found">
      Student not found.
    </div>
    <div v-else class="details-container">
      <div class="details-header">
        <h2>{{ studentInfo.student.firstName }} {{ studentInfo.student.lastName }}</h2>
        <div class="header-actions">
          <router-link :to="`/students/${studentInfo.student.studentNumber}/edit`" class="edit-button">
            Edit Student
          </router-link>
        </div>
      </div>

      <div class="card student-card">
        <h3>Student Information</h3>
        <div class="info-row">
          <div class="info-label">Student ID:</div>
          <div class="info-value">{{ studentInfo.student.studentNumber }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">First Name:</div>
          <div class="info-value">{{ studentInfo.student.firstName }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Last Name:</div>
          <div class="info-value">{{ studentInfo.student.lastName }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Email:</div>
          <div class="info-value">{{ studentInfo.student.mail }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Birth Date:</div>
          <div class="info-value">{{ studentInfo.student.birthDate || 'Not specified' }}</div>
        </div>
      </div>

      <div class="card academic-year-card" v-if="studentInfo.academicYear">
        <h3>Academic Year Information</h3>
        <div class="info-row">
          <div class="info-label">Formation Name:</div>
          <div class="info-value">{{ studentInfo.academicYear.formationName }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">TP Group Size:</div>
          <div class="info-value">{{ studentInfo.academicYear.tpGroupSize }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">TD Group Size:</div>
          <div class="info-value">{{ studentInfo.academicYear.tdGroupSize }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Optional Teaching Units:</div>
          <div class="info-value">{{ studentInfo.academicYear.optionalTeachingUnitsCount }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Responsible:</div>
          <div class="info-value">{{ studentInfo.academicYear.responsibleEmail }}</div>
        </div>
      </div>
      <div class="card no-academic-year" v-else>
        <h3>Academic Year</h3>
        <p>No academic year assigned to this student.</p>
      </div>

      <div class="enrollments-section">
        <h3>Enrollments</h3>
        
        <div class="enrollment-type">
          <h4>Groups</h4>
          <div v-if="studentInfo.groups && studentInfo.groups.length > 0" class="enrollment-list">
            <div v-for="groupId in studentInfo.groups" :key="`group-${groupId}`" class="enrollment-item">
              Group #{{ groupId }}
            </div>
          </div>
          <p v-else class="no-enrollments">No group enrollments.</p>
        </div>

        <div class="enrollment-type">
          <h4>Formations</h4>
          <div v-if="studentInfo.formations && studentInfo.formations.length > 0" class="enrollment-list">
            <div v-for="formationId in studentInfo.formations" :key="`formation-${formationId}`" class="enrollment-item">
              Formation #{{ formationId }}
            </div>
          </div>
          <p v-else class="no-enrollments">No formation enrollments.</p>
        </div>

        <div class="enrollment-type">
          <h4>Teaching Units (UE)</h4>
          <div v-if="studentInfo.modules && studentInfo.modules.length > 0" class="enrollment-list">
            <div v-for="moduleId in studentInfo.modules" :key="`module-${moduleId}`" class="enrollment-item">
              Module #{{ moduleId }}
            </div>
          </div>
          <p v-else class="no-enrollments">No teaching unit enrollments.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CoreService from '@/services/CoreService.js';

export default {
  name: 'StudentDetails',
  props: {
    id: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      studentInfo: null,
      loading: true,
      error: null
    };
  },
  created() {
    this.fetchStudentInfo();
  },
  methods: {
    async fetchStudentInfo() {
      this.loading = true;
      this.error = null;
      try {
        this.studentInfo = await CoreService.getStudentInfo(this.id);
      } catch (error) {
        this.error = 'Failed to load student information. Please try again later.';
        console.error(error);
      } finally {
        this.loading = false;
      }
    }
  },
  watch: {
    id() {
      this.fetchStudentInfo();
    }
  }
};
</script>

<style scoped>
.student-details {
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
  width: 150px;
  color: #6c757d;
}

.info-value {
  flex: 1;
}

.enrollments-section {
  margin-top: 20px;
}

.enrollment-type {
  margin-bottom: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.enrollment-type h4 {
  margin-bottom: 10px;
  color: #2c3e50;
}

.enrollment-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.enrollment-item {
  background-color: #e9ecef;
  padding: 8px 12px;
  border-radius: 4px;
  font-size: 0.9rem;
}

.no-enrollments {
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
