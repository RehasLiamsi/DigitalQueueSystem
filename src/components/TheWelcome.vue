<script setup>
import {onMounted, onUnmounted, ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const person = ref('');
const hasJoinedQueue = ref(sessionStorage.getItem('joinedQueue') === 'true');
const router = useRouter();
const positionInQueue = ref('');


const addPersonToQueue = async () => {
  if (!sessionStorage.getItem('joinedQueue')) {
    try {
      const response = await axios.post('http://localhost:8080/person/add/1');
      person.value = response.data;
      sessionStorage.setItem('person', JSON.stringify(response.data))
      sessionStorage.setItem('joinedQueue', 'true');
    } catch (error) {
      console.error("Error adding person to queue:", error);
    }
  } else {
    console.log('Already joined the queue', person.value.personId)
    const storedPerson = sessionStorage.getItem('person');
    person.value = JSON.parse(storedPerson);
  }
}

const fetchQueuePosition = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/person/${person.value.personId}/position`);
    positionInQueue.value = response.data;
  } catch (error) {
    console.log("Person ID: ", person.value.personId)
    console.error("Error fetching queue position:", error);
  }
}


const leaveQueue = async () => {
  try {
    await axios.put(`http://localhost:8080/person/left/${person.value.personId}`);
    sessionStorage.removeItem('joinedQueue');
    sessionStorage.removeItem('person');
    person.value = {};
    clearInterval();
    await router.push('/leftQueue')
  } catch (error) {
    console.error("Error leaving the queue:", error);
  }

}

onMounted(() => {
  const storedPerson = sessionStorage.getItem('person');
  if (storedPerson) {
    console.log('Person already in session storage')
    person.value = JSON.parse(storedPerson);
    sessionStorage.setItem('joinedQueue','true');
    fetchQueuePosition();
  } else {
    addPersonToQueue();
  }
  setInterval(fetchQueuePosition, 10000);

});
onUnmounted(() => {
  clearInterval();
})

</script>

<template>
  <div class="text-box">
    <p v-if="hasJoinedQueue">Din plats i kön är <span> {{ positionInQueue }}</span></p>
    <p v-else>Du måste ställa dig i kön genom att skanna QR coden</p>
    <button @click="leaveQueue()">Hoppa av kön</button>
  </div>
  <img src="../assets/360_F_543201527_CKqnLxlnnMjYJVLHDk6l69gzQwSfVh8X.jpg" alt="spinning arrow"/>
</template>
