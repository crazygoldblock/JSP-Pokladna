<%@page import="Core.Utils"%>
<%@page import="Core.Polozka"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <title>Restaurace</title>
</head>
<body>
  <h1>Restaurace</h1>

  <h2>Kategorie</h2>
  <select id="categorySelect" onchange="loadProducts()">
    <option value="">Vyber kategorii</option>
    <option value="Prilohy">Přílohy</option>
    <option value="Burgery">Burgery</option>
    <option value="Omacky">Omáčky</option>
    <option value="Napoje">Nápoje</option>
  </select>

  <h2>Položky</h2>
  <select id="productSelect">
    <option value="">Vyber položku</option>
  </select>

  <button onclick="addToOrder()">Přidat do objednávky</button>

  <h2>Objednávka:</h2>
  <ul id="orderList"></ul>

  <script>
    const products = {
      Prilohy: [
        <% 

        Polozka[] polozky = Utils.GetPrilohy();

        String text = "";

        for (Polozka polozka : polozky) {
            text += "{ name: \"" + polozka.toString() + "\", price: " + polozka.GetCena() + "},";
        }

        out.println(text);
        
        %>
      ],
      Burgery: [
        <% 

        Polozka[] polozky = Utils.GetBurgery();

        String text = "";

        for (Polozka polozka : polozky) {
            text += "{ name: \"" + polozka.toString() + "\", price: " + polozka.GetCena() + "},";
        }

        out.println(text);
        
        %>
      ],
      Omacky: [
        <% 

        Polozka[] polozky = Utils.GetOmacky();

        String text = "";

        for (Polozka polozka : polozky) {
            text += "{ name: \"" + polozka.toString() + "\", price: " + polozka.GetCena() + "},";
        }

        out.println(text);
        
        %>
      ]
      ,
      Napoje: [
        <% 

        Polozka[] polozky = Utils.GetNapoje();

        String text = "";

        for (Polozka polozka : polozky) {
            text += "{ name: \"" + polozka.toString() + "\", price: " + polozka.GetCena() + "},";
        }

        out.println(text);
        
        %>
      ]
    };

    let order = [];

    function loadProducts() {
        const categorySelect = document.getElementById("categorySelect");
        const productSelect = document.getElementById("productSelect");
        const selectedCategory = categorySelect.value;

        productSelect.innerHTML = "<option value=''>Vyber položku</option>";

        if (selectedCategory && products.hasOwnProperty(selectedCategory)) {
            const categoryProducts = products[selectedCategory];

            categoryProducts.forEach((product) => {
                const option = document.createElement("option");
                option.value = product.name;
                option.textContent = product.name;
                productSelect.appendChild(option);
            });
        }
    }

    function addToOrder() {
        const productSelect = document.getElementById("productSelect");
        const selectedProduct = productSelect.value;

        if (selectedProduct) {
            const categorySelect = document.getElementById("categorySelect");
            const selectedCategory = categorySelect.value;
            const selectedPrice = products[selectedCategory].find(
                (product) => product.name === selectedProduct
            ).price;

            const item = {
                category: selectedCategory,
                product: selectedProduct,
                price: selectedPrice
            };

            order.push(item);

            const orderList = document.getElementById("orderList");
            const listItem = document.createElement("li");
            listItem.textContent = `${item.product} - ${item.price} Kč`;
            orderList.appendChild(listItem);

            productSelect.value = "";
        }
    }
  </script>
</body>
</html>