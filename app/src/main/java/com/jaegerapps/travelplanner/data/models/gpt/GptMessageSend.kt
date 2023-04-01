package com.jaegerapps.travelplanner.data.models.gpt

import com.google.gson.GsonBuilder
import com.jaegerapps.travelplanner.R


data class GptMessageSend(
    val role: String,
    val content: String,
) {

    companion object {
        val baseSpecList = arrayOf(
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
    }
}