package com.jaegerapps.travelplanner.data.models.gpt


data class GptMessageSend(
    val role: String,
    val content: String,
) {

    companion object {
        val baseRequestList = arrayOf(
            GptMessageSend(
                role = "system",
                content = "You are a travel planner. " +
                        "You are given the length of travel plans, the city in a country, and types of activity the user wants to do." +
                        "You will be given a list of places from Google Places API." +
                        "Based off the given information, plan a travel itinerary for the day using places from the Google Places list. Do not add or create new places that are not on the list.  " +
                        "Format the response in the follow json format. Ignore backslashes in response. Apply spacing between words in a sentence: " +
                        "{\"itinerary\":{\"location\":\"STRING\",\"length\":123,\"interests\":\"STRING\",\"day_plan\":{\"day\":123,\"events\":123,\"plans\":[{\"name\":\"STRING\",\"description\":\"STRING\",\"keywords\":\"STRING\",\"type\":\"STRING\"},{\"name\":\"STRING\",\"description\":\"STRING\",\"keywords\":\"STRING\",\"type\":\"STRING\"}],}}}" +
                        "If you are unable to find something, put not_found as the description." +
                        "Do not return anything but valid JSON. Validate JSON before returning."
            ),
            GptMessageSend(
                role = "user",
                content =
                "I am traveling to Berlin, Germany for 1 day.Here is a little about my trip: I am visiting relatives in Germany and want to tour the city with them.. I am interested in culture, museums, big city vibes.[Here is a list of valid locations to select from. Do choose or make locations that are not on this list., [Brandenburg Gate, Berliner Fernsehturm, German Historical Museum, Little BIG City Berlin, Memorial to the Murdered Jews of Europe, Victory Column, Berlin Wall, Charlottenburg Palace, Zeughaus, Tränenpalast, Berlin Wall Memorial, James-Simon-Park, Gendarmenmarkt, Checkpoint Charlie, Reichstag Building, Potsdamer Platz, Barnimstraße 10 – Gefängnis für Frauen, East Side Gallery, Berlin Story Bunker, Denkmal Rosenstraße]][]"
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"itinerary\":{\"location\":\"Berlin, Germany\",\"length\":1,\"interests\":\"Culture, Museums, Big City Vibes\",\"day_plan\":{\"day\":1,\"events\":3,\"plans\":[{\"name\":\"Brandenburg Gate\",\"description\":\"The most famous landmark of Berlin, a symbol of unity and peace, built in 1791.\",\"keywords\":\"historical monument, sightseeing\",\"type\":\"sightseeing\"},{\"name\":\"German Historical Museum\",\"description\":\"A museum of German history from ancient times to present day.\",\"keywords\":\"museum, history\",\"type\":\"sightseeing\"},{\"name\":\"East Side Gallery\",\"description\":\"A former section of the Berlin Wall, now painted with murals by international artists.\",\"keywords\":\"history, street art\",\"type\":\"sightseeing\"}],\"transportation\":[]}}}"
            ),
            GptMessageSend(
                role = "user",
                content = "I am traveling to Tokyo, Japan for 1 day.Here is a little about my trip: I am a solo traveller looking to see the big city of Tokyo.. I am interested in big city, culture.[Here is a list of valid locations to select from. Do choose or make locations that are not on this list., [Tokyo Tower, Meiji Jingu Gaien, Tokyo Metropolitan Government Building observation room, Hachikō Memorial Statue, Meiji Jingu Gyoen, Meiji Jingu, shin Godzilla Statue, Tokyo Garden Terrace Kioicho, Omoide Yokochō, The National Art Center, Tokyo, Yoyogi Park, Tokyo Camii \\u0026 Diyanet Turkish Culture Center, Tokyo Skytree, Shibuya Sky, Tokyo Metropolitan Government Building, Ninja Trick House In Tokyo, Meiji Jingu Kaguraden, Bunkamura, Hyōgo Yokochō Alley, Shinjuku Gyoen National Garden]][]"
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"itinerary\":{\"location\":\"Tokyo, Japan\",\"length\":1,\"interests\":\"Big City, Culture\",\"day_plan\":{\"day\":1,\"events\":3,\"plans\":[{\"name\":\"Tokyo Metropolitan Government Building observation room\",\"description\":\"An observation deck on the 45th floor offering panoramic views of Tokyo. \",\"keywords\":\"observation deck, sightseeing, big city\",\"type\":\"sightseeing\"},{\"name\":\"The National Art Center, Tokyo\",\"description\":\"An art museum featuring exhibitions of contemporary and modern art from around the world. \",\"keywords\":\"art, museum, culture\",\"type\":\"sightseeing\"},{\"name\":\"Shibuya Sky\",\"description\":\"Shibuya Sky is a 360° open-air observation deck located at the rooftop of Shibuya Scramble Square offering panoramic views of Tokyo, with an indoor section, hammocks, observation compass, and a café/bar. \",\"keywords\":\"big city, nightlife, culture\",\"type\":\"sightseeing\"}]}}}"
            ),
            GptMessageSend(
                role = "user",
                content = "I am traveling to Danang, Vietnam for 1 day.Here is a little about my trip: I am a solo traveller. . I am interested in culture, relaxation, beach.[Here is a list of valid locations to select from. Do choose or make locations that are not on this list., [Da Nang Museum of Cham Sculpture, 3D Museum Art in paradise Da Nang, Asia Park - Sun World Da Nang Wonders, Đà Nẵng Cathedral, Con Market, Ghềnh Bàng, Sun Wheel, Mũi Nghê, The Marble Mountains, Da Nang Fresco Village, Danang Fine Arts Museum, Dragon Bridge, Da Nang Food Tour, Hòn Thủy Sơn, Lunar castle, Đà Nẵng Museum, Hoa Nghiem Cave, Grass Square, Phước Mỹ Beach, Dong Dinh Art Museum]][]"
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"itinerary\":{\"location\":\"Danang, Vietnam\",\"length\":1,\"interests\":\"Culture, Relaxation, Beach\",\"day_plan\":{\"day\":1,\"events\":3,\"plans\":[{\"name\":\"Da Nang Museum of Cham Sculpture\",\"description\":\"A museum showcasing the culture and history of the Cham civilization.\",\"keywords\":\"culture, museum\",\"type\":\"sightseeing\"},{\"name\":\"Con Market\",\"description\":\"A beautiful white sand beach with clear blue water, perfect for swimming or relaxing on the shore.\",\"keywords\":\"market, food, shopping\",\"type\":\"sightseeing\"},{\"name\":\"Dragon Bridge\",\"description\":\"A bridge that crosses the Han River and is known for its dragon-shaped design, it is an attraction to see a fire and water show.\",\"keywords\":\"culture, landmark, design\",\"type\":\"sightseeing\"}]}}}"
            ),
        )

        val baseFilterList = arrayOf(
            GptMessageSend(
                role = "system",
                content = "You are a travel planner's assistant. " +
                        "You are given a traveller's interests, about their trip, and a list of places from Google Places API. The list contains names and the type." +
                        "Based off the given information, filter the list down to 5 items that fit the user's interests and about trip. Only use the information given from the list. Do not make, create, or add new things that are not from the list. " +
                        "Format the response in the follow json format. Ignore backslashes in response. Apply spacing between words in a sentence: " +
                        "{\"places\":[\"STRING\",\"STRING\",\"STRING\",\"STRING\",\"STRING\"]}" +
                        "Do not return anything but valid JSON. Validate JSON before returning."
            ),
            GptMessageSend(
                role = "user",
                content =
                "I am traveling to Bali, Indonesia for 1 day.Here is a little about my trip:  I have never been to Bali, and I really want to see all of the amazing places in Bali!. I am interested in [tourist attraction, landmarks, parks].[Here is a Google Places list of valid locations to select from:, Leke Leke Waterfall, Air Terjun. Twin waterfall Lipah Petang, Sky Swing Bali, Bayan Ancient Tree, SURYA BINTANG ADVENTURES, Rimba swing Bali, DESA WISATA PINGE, Babahan Crystal Water, Secret Garden Village, Wahana Wisata Sambhrama Ashram, Air Terjun Yeh Hoo, Bedugul, bali hideaway bike, Rafting, True Bali Experience, Wisata Kayu Putih][]"
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"places\":[\"Leke Leke Waterfall\",\"Bedugul\",\"Secret Garden Village\",\"Air Terjun Yeh Hoo\",\"Babahan Crystal Water\"]}"


            ),
            GptMessageSend(
                role = "user",
                content = "I am traveling to New York, NY, USA for 1 day.Here is a little about my trip: I want to see all of the big excitements of the big apple!. I am interested in [tourist attraction, night life, street food].[Here is a Google Places list of valid locations to select from:, One World Observatory, The Channel Gardens, Empire State Building, One Times Square, Top of The Rock, Flatiron Building, Statue City Cruises Battery Park/ Liberty Island, McGraw-Hill Waterfall, SUMMIT One Vanderbilt, Gantry Plaza State Park, Hudson River Park Trust, Chrysler Building, Statue of Liberty new york, SPYSCAPE, RiseNY, Times Square, Pier 15, East River Esplanade, Mini Plexiglass Waterfall Tunnel, The Battery, Flag Plaza, 254 Broadway][]"
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"places\":[\"Times Square\",\"Gantry Plaza State Park\",\"Pier 15\",\"One World Observatory\",\"The Battery\"]}"
            ),
           /* GptMessageSend(
                role = "user",
                content =
                "I am traveling to San Francisco, CA, USA for 1 day.Here is a little about my trip: I am interested in the food culture in San Francisco. I have heard a lot about the food trucks and local food.. I am interested in [street food, parks, restaurants].[Here is a Google Places list of valid locations to select from:, Sowl Bowls Thai Street Food, Sumac | Modern Street Food, Saap Ver Damn Good! Thai street food, Hippie Thai Street Food, Street Taco, Shihlin Taiwan Street Snacks, Aria Korean Street Food, iThai Bangkok Street Food, DragonEats, Street Taco, Za\u0027atar | Levantine Inspired Street Food, DragonEats, Kababayan Fast Food, Street Restaurant and Bar, Hawker Eats, Hello Sandwich \u0026 Noodle, Street Taco, DragonEats, The Chairman, Mau Restaurant, 1500-1514 Market St][]"
            ),*/
            /*GptMessageSend(
                role = "assistant",
                content =
                "{\"places\":[\"The Chairman\",\"Sowl Bowls Thai Street Food \",\"Sumac | Modern Street Food\",\"Hawker Eats\",\"Za'atar | Levantine Inspired Street Food\"]}"
            ),*/
        )
    }
}