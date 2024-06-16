// script.js file
function domReady(fn) {
    if (
        document.readyState === "complete" ||
        document.readyState === "interactive"
    ) {
        setTimeout(fn, 1000);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}

domReady(function () {
    let isProcessing = false;
    let scanTimeout;

    // If found you qr code
    function onScanSuccess(decodeText, decodeResult) {
        handleCallApi(decodeText, decodeResult);
    }

    function onScanSuccess_1(decodeText, decodeResult) {
            handleCallApi(decodeText, decodeResult);
    }

    function onScanSuccess_2(decodeText, decodeResult) {
        handleCallApi(decodeText, decodeResult);
    }

    function onScanSuccess_3(decodeText, decodeResult) {
        handleCallApi(decodeText, decodeResult);
    }

    function onScanSuccess_4(decodeText, decodeResult) {
        handleCallApi(decodeText, decodeResult);
    }

    function handleCallApi(decodeText, decodeResult){
        if (!isProcessing) {
            isProcessing = true;
            console.log(decodeText, decodeResult);
            $.ajax({
                url: '/api/admin/sell-off/product-detail/get-by-id/' + decodeText,
                method: 'GET',
                success: function (response) {
                    console.log(response);
                    var id = Number(decodeText);
                    swal.fire({
                        icon: "success",
                        text: response.sp.tenSP + " : Size " + response.size + " & Màu " + response.ms.ten + ".",
                    }).then((result) => {
                        if (result.isConfirmed) {
                            addToInvoiceDetail(id);
                        }
                        isProcessing = false;
                    })

                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });
        }
        // Đặt thời gian chờ giữa các lần quét (ví dụ: 2 giây)
        clearTimeout(scanTimeout); // Xóa bỏ bất kỳ thời gian chờ nào đang chờ
        scanTimeout = setTimeout(function () {
            isProcessing = false; // Đánh dấu là không còn xử lý nữa sau khoảng thời gian chờ
        }, 5000); // Thiết lập thời gian chờ là 2 giây (2000 milliseconds)
    }

    let htmlscanner = new Html5QrcodeScanner(
        "my-qr-reader",
        {fps: 10, qrbos: 250, disableFlip: false}
    );
    htmlscanner.render(onScanSuccess);

    let htmlscanner1 = new Html5QrcodeScanner(
        "my-qr-reader-1",
        {fps: 10, qrbos: 250, disableFlip: false}
    );
    htmlscanner1.render(onScanSuccess_1);

    let htmlscanner2 = new Html5QrcodeScanner(
        "my-qr-reader-2",
        {fps: 10, qrbos: 250, disableFlip: false}
    );
    htmlscanner2.render(onScanSuccess_2);

    let htmlscanner3 = new Html5QrcodeScanner(
        "my-qr-reader-3",
        {fps: 10, qrbos: 250, disableFlip: false}
    );
    htmlscanner3.render(onScanSuccess_3);

    let htmlscanner4 = new Html5QrcodeScanner(
        "my-qr-reader-4",
        {fps: 10, qrbos: 250, disableFlip: false}
    );
    htmlscanner4.render(onScanSuccess_4);
});


