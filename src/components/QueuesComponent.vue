<script setup>
import "bootstrap/dist/css/bootstrap.css";
import BannerComponent from "@/components/BannerComponent.vue";
import {onMounted, ref} from "vue";
import axiosInstance from "@/axiosConfig";

const queues = ref('');
const fields = ['Queue Name', 'Queue Status'];
const queueName = ref('');

const fetchAllQueues = async () => {
  const response = await axiosInstance.get('queue');
  queues.value = response.data;
}

const getStatusText = (status) => {
  return status ? 'Active' : 'Inactive';
};

const dropAllPersonsFromQueue = async (queueName) => {
  await axiosInstance.put(`person/left/all/${queueName}`)
}

const toggleStatus = async (queueName, queueId) => {
  await dropAllPersonsFromQueue(queueName);
  await axiosInstance.put(`queue/${queueId}`);
  await fetchAllQueues();
}

const deleteQueue = async (queueId) => {
  const confirmed = window.confirm("Are you sure you want to delete this queue?");
  if (confirmed) {
    try {
      await axiosInstance.delete(`queue/delete/${queueId}`);
      await fetchAllQueues();
    } catch (error) {
      window.alert("OOPS! Error deleting queue.")
    }
  }
}

const addQueue = async () => {
  const details = {queueName: queueName.value, queueStatus: 0};
  try {
    await axiosInstance.post('/queue', details);
    await fetchAllQueues();
  } catch (error) {
    window.alert("Error adding queue!")
  }
}

onMounted(fetchAllQueues);
</script>
<template>
  <BannerComponent/>
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
          <button type="button" class="btn btn-outline-primary btn-sm" @click="toggleStatus(queue.queueName, queue.queueId)">
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