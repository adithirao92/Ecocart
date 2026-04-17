const API = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", loadWishlist);

async function loadWishlist() {

    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("Login first!");
        return;
    }

    const res = await fetch(`${API}/users/wishlist/${userId}`);
    const data = await res.json();

    const container = document.getElementById("wishlist-container");
    container.innerHTML = "";

    (data.products || []).forEach(p => {

        if (!p) return;

        container.innerHTML += `
            <div class="col-md-3 mb-3">
                <div class="card">
                    <img src="${p.imageUrl}" class="card-img-top" style="height:150px;object-fit:cover">

                    <div class="card-body">
                        <h6>${p.name}</h6>
                        <p>₹${p.price}</p>

                        <button class="btn btn-danger btn-sm"
                            onclick="removeFromWishlist(${p.productId})">
                            Remove ❌
                        </button>
                    </div>
                </div>
            </div>
        `;
    });
}

async function removeFromWishlist(productId) {

    const userId = localStorage.getItem("userId");

    await fetch(
        `${API}/users/wishlist/remove?userId=${userId}&productId=${productId}`,
        { method: "DELETE" }
    );

    loadWishlist();
}