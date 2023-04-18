package com.jaegerapps.travelplanner.data.remote

val valid_gpt_itinerary_response = """
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
            "content":"{\"itinerary\":{\"location\":\"Seoul, South Korea\",\"length\":1,\"interests\":\"Tourist Attraction, Parks, Night Life\",\"day_plan\":{\"day\":1,\"events\":3,\"plans\":[{\"name\":\"Namsan Seoul Tower\",\"description\":\"a well-known tourist attraction with a panoramic view of Seoul from an observation deck at the top of the tower.\",\"keywords\":\"tourist attraction, observation deck\",\"type\":\"sightseeing\"},{\"name\":\"Seoul Forest Park\",\"description\":\"a large park with a variety of walking paths, bike rentals, nature exhibits, and playgrounds.\",\"keywords\":\"park, nature, relaxation\",\"type\":\"sightseeing\"},{\"name\":\"Thursday Party\",\"description\":\"a popular night club with a fun atmosphere and great music.\",\"keywords\":\"nightlife, party, club\",\"type\":\"nightlife\"}]}}}"
          },
          "finish_reason": "stop",
          "index": 0
        }
      ]
    }
""".trimIndent()