console.log("this is script file");

const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //true
    //band karna hai
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    //false
    //show karna hai
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

const search = () => {
  // console.log("searching...");

  let query = $("#search-input").val();

  if (query == "") {
    $(".search-result").hide();
  } else {
    //search
    console.log(query);

    //sending request to server

    let url = `http://localhost:8282/search/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        //data......
        // console.log(data);

        let text = `<div class='list-group'>`;

        data.forEach((contact) => {
          text += `<a href='/user/${contact.cId}/contact' class='list-group-item list-group-item-action'> ${contact.name}  </a>`;
        });

        text += `</div>`;

        $(".search-result").html(text);
        $(".search-result").show();
      });
  }
};

//first request- to server to create order

const paymentStart = () => {
  console.log("payment started..");
  var amount = $("#payment_field").val();
  console.log(amount);
  if (amount == "" || amount == null) {
    // alert("amount is required !!");
    swal("Failed !!", "amount is required !!", "error");
    return;
  }

 // Create order by calling server api
  $.ajax({
    url: "/payment/create_order",
    data: JSON.stringify({ amount: amount, info: "order_request" }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      //invoked when success
      console.log(response);
      if (response.status == "created") {
        //open payment form
        let options = {
          key: "rzp_test_cyqsJROm0DNe9o",
          amount: response.amount,
          currency: "INR",
          name: "Smart Contact Manager",
          description: "Donation",
          image:
            "https://yt3.ggpht.com/-4BGUu55s_ko/AAAAAAAAAAI/AAAAAAAAAAA/3Cfl_C4o8Uo/s108-c-k-c0x00ffffff-no-rj-mo/photo.jpg",
          order_id: response.id,
          
          // If we remove 'callback_url' & 'redirect' property, then there will not be any call back redirection from the payment gateway 
          // when payment is successful rather the response will be served as an ajax response for this ajax call.  
          callback_url: 'http://localhost:8282/payment/payment-status',
          redirect: true,
          
          handler: function (response) {
            console.log("PAYMENT ID : "+response.razorpay_payment_id);
            console.log("ORDER ID : "+response.razorpay_order_id);
            console.log("SIGNATURE : "+response.razorpay_signature);
            console.log("payment successful !!");
            swal("Good job!", "congrates !! Payment successful !!", "success");
          },
          prefill: {
            name: "Himanshubhusan Rath",
            email: "him@him.com",
            contact: "9191919191",
          },

          notes: {
            address: "Bangalore",
          },
          theme: {
            color: "#3399cc",
          },
        };

        let rzp = new Razorpay(options);

        rzp.on("payment.failed", function (response) {
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);
          //alert("Oops payment failed !!");
          swal("Failed !!", "Oops payment failed !!", "error");
        });

        rzp.open();
      }
    },
    error: function (error) {
      //invoked when error
      console.log(error);
      alert("something went wrong !!");
    },
  });
};
