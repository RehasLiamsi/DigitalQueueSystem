<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";

const person = ref('');

const addPersonToQueue = async () => {
  try {
    const response = await axios.post('http://localhost:8080/person/add/2');
    person.value = response.data;
  } catch (error) {
    console.error("Error adding person to queue:", error);
  }
}
onMounted(addPersonToQueue);
</script>

<template>
  <div class="text-box">
    <p>Din plats i kön är <span> {{person.positionInQueue}}</span></p>
    <p></p>
  </div>
  <img src="../assets/360_F_543201527_CKqnLxlnnMjYJVLHDk6l69gzQwSfVh8X.jpg" alt="spinning arrow" />
</template>
