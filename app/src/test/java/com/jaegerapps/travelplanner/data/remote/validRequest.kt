package com.jaegerapps.travelplanner.data.remote

val valid_request = """
    {
        "model": "gpt-3.5-turbo",
        "messages": [{
            "role":"user",
            "content": "{"length":3,"location":"Busan, Korea","interests":"historical, modern art, local food","specialRequests":"I want to go to a local market and eat the street food.","transportation":false,"findRestaurants":true,"mealTypes":{"meal":"lunch","cuisine":"local food","foodRequest":"I don\u0027t want to eat bugs or squid."}}"
        }],
        "temperature":"1.0"
    }
""".trimIndent()