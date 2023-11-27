// /*<![CDATA[*/
//    let price= 778008;
//
//
//    console.log(price);
//
//    const IMP = window.IMP;
//    IMP.init("imp18267031");
//
//
//
//    function requestPay() {
//
//        IMP.request_pay({
//            pg: "kakaopay.TC0ONETIME",
//            pay_method: "kakaopay",
//            merchant_uid: 'patient_' + new Date().getTime(),
//            name: '병원비 내기',
//            amount: price,
//            reservationId:32,
//            hospital_name: "차앤박 병원",
//            patient_name: "watch",
//
//        }, function (rsp) { // callback
//                   if (rsp.success) {// 결제성공시 로직
//                       let data = { // request
//           				imp_uid:rsp.imp_uid,
//           				amount:rsp.paid_amount,
//           				reservationId: 32
//           			};
//                       //결제 검증
//                       $.ajax({
//           				type:"POST",
//           				url:"/patient/vertifyIamport",
//           				data:JSON.stringify(data),
//           				contentType:"application/json; charset=utf-8",
//           				dataType:"json",
//           				success: function(result) {
//           					alert("결제 및 결제 검증이 완료되었습니다.");
//           					//self.close();
//           				},
//           				error: function(result){
//           					alert(result.responseText);
//
//           				}
//           			});
//
//                   } else {// 결제 실패 시 로직
//           			alert("결재 실패");
//           			//alert(rsp.error_msg);
//           			//console.log(rsp);
//         }
//       	});
//       }//requestPay
//
//    /*]]>*/