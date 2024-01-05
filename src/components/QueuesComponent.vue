<script setup>
import "bootstrap/dist/css/bootstrap.css";
import BannerComponent from "@/components/BannerComponent.vue";
import {onMounted, ref} from "vue";
import axiosInstance from "@/axiosConfig";

const queues = ref('');
const fields = ['ID', 'Queue Name', 'Queue Status'];

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
      <th v-for="field in fields" :key='field' @click="sortTable(field)">
        {{ field }} <i class="bi bi-sort-alpha-down" aria-label='Sort Icon'></i>
      </th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="queue in queues" :key="queue">
      <td>{{ queue.queueId }}</td>
      <td>{{ queue.queueName }}</td>
      <td>
        <button type="button" class="btn btn-outline-primary btn-sm" @click="toggleStatus(queue.queueId)">{{ getStatusText(queue.queueStatus) }}</button>
      </td>
    </tr>
    </tbody>
  </table>
  </div>
</template>
<style scoped>
</style>