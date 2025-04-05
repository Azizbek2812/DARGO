const input1 = document.getElementById('input1');
const input2 = document.getElementById('input2');
const input3 = document.getElementById('input3');
const input4 = document.getElementById('input4');
const input5 = document.getElementById('input5');
const userPhone = localStorage.getItem("userPhoneNumber");
document.getElementById('userPhone').textContent = userPhone;

function handleInput(event) {
    /*const enteredValue = event.target.value;
    const elementId = event.target.id;
    const nextInput = event.target.nextElementSibling;*/

    const enteredValue = event.target.value;
    const nextInput = event.target.nextElementSibling;
    if (enteredValue && nextInput) {
        nextInput.focus();
    } else if (nextInput === null) {
        handleSubmit();
    }
}

function handleSubmit() {
    // send confirm request
    let code = null;
    if (input1.value && input2.value && input3.value && input4.value && input5.value) {
        code = input1.value + input2.value + input3.value + input4.value + input5.value;
    }
    if (code == null) { // todo
        return;
    }

    const body = {
        "phone": userPhone,
        "code": code
    }

    const lang = document.getElementById("current-lang").textContent;
}

function resendSms() {
    // sms ni qayta jo'natish
    const body = {
        "phone": userPhone
    }

    const lang = document.getElementById("current-lang").textContent;
}

function clearInput(){
    input1.value = '';
    input2.value = '';
    input3.value = '';
    input4.value = '';
    input5.value = '';
}

input1.addEventListener('input', handleInput);
input2.addEventListener('input', handleInput);
input3.addEventListener('input', handleInput);
input4.addEventListener('input', handleInput);
input5.addEventListener('input', handleInput);