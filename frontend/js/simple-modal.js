// JavaScript to handle modal open and close
const modal = document.getElementById('simpleModalId');
const openModalBtn = document.getElementById('openModalBtn');
const closeModalBtn = document.getElementById('closeModalBtn');
const closeModalFooter = document.getElementById('closeModalFooter');

openModalBtn.onclick = () => modal.style.display = 'block';
closeModalBtn.onclick = () => modal.style.display = 'none';
closeModalFooter.onclick = () => modal.style.display = 'none';

// Close modal when clicking outside the content
window.onclick = (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};