<script setup>
import "bootstrap/dist/css/bootstrap.css";
import BannerComponent from "@/components/BannerComponent.vue";
import {onMounted, ref} from "vue";
import axiosInstance from "@/axiosConfig";

const queues = ref('');
const fields = ['Queue Name', 'Queue Status'];
const queueName = ref('');

const fetchAllQueues = async () => {
  try {
    const response = await axiosInstance.get('queue');
    queues.value = response.data;
  } catch (error) {
    console.log("Error fetching all queues:", error);
  }
}

const getStatusText = (status) => {
  return status ? 'Active' : 'Inactive';
};

const toggleStatus = async (queueId) => {
  try {
    await axiosInstance.put(`queue/${queueId}`);
    await fetchAllQueues();
  } catch (error) {
    console.log("Error changing queue status:", error);
  }
}

const deleteQueue = async (queueId) => {
  const confirmed = window.confirm("Are you sure you want to delete this queue?");
  if (confirmed) {
    try {
      await axiosInstance.delete(`queue/delete/${queueId}`);
      await fetchAllQueues();
    } catch (error) {
      console.log("Error deleting queue", error)
    }
  }
}

const addQueue = async() => {
  const details = { queueName: queueName.value, queueStatus: 0 };
  try {
    await axiosInstance.post('/queue', details);
    await fetchAllQueues();
  } catch (error) {
    console.error('Error adding Queue', error);
  }
}

onMounted(fetchAllQueues);
//setInterval(fetchAllQueues,1000);
</script>
<template>
  <BannerComponent/>
  <!--  <ul v-for="queue in queues" :key="queue">-->
  <!--    <li>{{queue.queueName}}</li>-->
  <!--    <li>{{queue.queueStatus}}</li>-->
  <!--  </ul>-->
  <div class="m-5">
    <table class="table table-hover table-bordered ">
      <thead>
      <tr>
        <th v-for="field in fields" :key='field'>
          {{ field }} <i class="bi bi-sort-alpha-down" aria-label='Sort Icon'></i>
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="queue in queues" :key="queue">
        <td>{{ queue.queueName }}</td>
        <td>
          <button type="button" class="btn btn-outline-primary btn-sm" @click="toggleStatus(queue.queueId)">
            {{ getStatusText(queue.queueStatus) }}
          </button>
        </td>
        <td>
          <button type="button" class="btn btn-outline-primary btn-sm" @click="deleteQueue(queue.queueId)">Delete
            Queue
          </button>
        </td>
      </tr>
      <tr>
        <td>
          <input type="text" id="queueName" v-model="queueName" required placeholder="Queue Name">
        </td>
<!--        <td>-->
<!--          <button type="button" class="btn btn-outline-primary btn-sm" @click="toggleStatus(queue.queueId)">-->
<!--            {{ getStatusText(queue.queueStatus) }}-->
<!--          </button>-->
<!--        </td>-->
        <td>
          <button type="button" class="btn btn-outline-primary btn-sm" @click="addQueue()">Add Queue
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<style scoped>
</style>