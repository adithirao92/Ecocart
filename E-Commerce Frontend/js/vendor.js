const API_BASE = "http://localhost:8080/api";

// AUTH CHECK
document.addEventListener("DOMContentLoaded", () => {
    const role = localStorage.getItem("userMode");

    if (role !== "vendor") {
        alert("Access denied");
        window.location.href = "index.html";
    }

    document.getElementById("vendor-name").innerText =
        localStorage.getItem("username");

    loadProducts();
});

// LOAD PRODUCTS
async function loadProducts() {
    const res = await fetch(`${API_BASE}/products`);
    const data = await res.json();

    displayProducts(data.products || []);
}

// DISPLAY PRODUCTS
function displayProducts(products) {
    const container = document.getElementById("products-container");
    container.innerHTML = "";

    products.forEach(p => {
        container.innerHTML += `
            <div class="col-md-3 mb-3">
                <div class="card">
                    <img src="${p.imageUrl}" class="card-img-top" style="height:150px;object-fit:cover">
                    <div class="card-body">
                        <h6>${p.name}</h6>
                        <p>₹${p.price}</p>

                        <button class="btn btn-danger btn-sm"
                            onclick="deleteProduct(${p.productId})">
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        `;
    });
}

// ADD PRODUCT
document.getElementById("add-product-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const product = {
        name: productName.value,
        price: parseFloat(productPrice.value),
        stockQuantity: parseInt(productStock.value),
        imageUrl: productImage.value,
        description: productDescription.value,
        categoryId: parseInt(productCategory.value)
    };

    const res = await fetch(`${API_BASE}/products`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product)
    });

    if (res.ok) {
        alert("Product added!");
        loadProducts();
    }
});

// DELETE PRODUCT
async function deleteProduct(id) {
    await fetch(`${API_BASE}/products/${id}`, {
        method: "DELETE"
    });

    loadProducts();
}

// LOGOUT
function logout() {
    localStorage.clear();
    window.location.href = "index.html";
}