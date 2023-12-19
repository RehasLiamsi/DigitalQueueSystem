<script setup>
import BannerComponent from "@/components/BannerComponent.vue";
import {ref} from "vue";
import axiosInstance from "@/axiosConfig";

const queues = ref('');

const newQueue = ref('');

const fetchAllQueues = async() => {

}

function addNewTask() {
  if (newQueue.value.trim() === "") return;
  tasks.value.push({ text: newQueue.value, completed: false });
  newQueue.value = "";
}

const addNewQueue = async(details) => {
  try{
    const response = await axiosInstance.post('queue');

  }
}

function removeTask(task) {
  tasks.value = tasks.value.filter((remove) => remove !== task);
}
</script>
<template>
  <BannerComponent />
    <p>The following are the queues</p>
  <div id="container">
    <div id="queue-list">
      <ul>
        <li v-for="queue in queues" :key="queue.text" :class="{ completed: queue.completed }">
          <input type="checkbox" v-model="queue.completed" />
          {{ queue.text }}
          <button id="remove-button" @click="removeTask(queue)">Delete</button>
        </li>
      </ul>
    </div>
    <div id="add-new-task">
      <textarea v-model="newQueue" placeholder="Add new task"></textarea>
      <button id="add-button" @click="addNewTask">Add</button>
    </div>
  </div>
</template>
<style scoped></style>