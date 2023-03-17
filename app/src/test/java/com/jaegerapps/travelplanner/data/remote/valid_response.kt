package com.jaegerapps.travelplanner.data.remote

val validResponse = """
    {
      "id": "chatcmpl-6ug9wkFQRYOre0jjPWSmpXeTkafny",
      "object": "chat.completion",
      "created": 1678966448,
      "model": "gpt-3.5-turbo-0301",
      "usage": {
        "prompt_tokens": 1384,
        "completion_tokens": 198,
        "total_tokens": 1582
      },
      "choices": [
        {
          "message": {
            "role": "assistant",
            "content": "{\"itinerary\":{\"location\":\"Tokyo, Japan\",\"length\":1,\"interests\":\"Local food and shopping\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"5 Chome-2-1 Tsukiji, Chuo City, Tokyo 104-0045, Japan\",\"name\":\"Tsukiji Outer Market\",\"description\":\"A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.\",\"keywords\":\"local food,shopping,market\",\"type\":\"sightseeing\"},{\"address\":\"1 Chome-8-3 Jingumae, Shibuya City, Tokyo 150-0001, Japan\",\"name\":\"Omotesando\",\"description\":\"A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.\",\"keywords\":\"shopping,food,fashion brands,architecture\",\"type\":\"sightseeing\"}],\"transportation\":[]}}}"
          },
          "finish_reason": "stop",
          "index": 0
        }
      ]
    }
""".trimIndent()