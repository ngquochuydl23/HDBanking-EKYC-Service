const twilio = require("twilio"); 


const accountSid = process.env.TWILIO_ACCOUNT_SID;
const authToken = process.env.TWILIO_AUTH_TOKEN;
const client = twilio(accountSid, authToken);

async function createMessage() {
  const message = await client.messages.create({
    body: "Mã OTP của bạn: 123456",
    from: process.env.TWILLI_PHONE,
    to: "+84868684961",
  });

  console.log(message.body);
}


module.exports = {
  createMessage
};
