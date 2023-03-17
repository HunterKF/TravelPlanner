package com.jaegerapps.travelplanner.data.models

import com.google.gson.GsonBuilder


data class GptMessageSend(
    val role: String,
    val content: String,
) {
    fun GptMessageToJson(): String? {
        val gson = GsonBuilder().enableComplexMapKeySerialization().create()

        val complexMap2: LinkedHashMap<GptMessageSend, String> = LinkedHashMap<GptMessageSend, String>()
        complexMap2[GptMessageSend(role = role, content =content)]
        val json = gson.toJson(complexMap2)
        return json
    }

    fun toArray(): String {
        return role + content
    }

    companion object {
        val baseSpecList = arrayOf(
            GptMessageSend(
                role = "system",
                content = "You are a travel planner. " +
                        "You are given the length of travel plans, the city in a country, types of activity the user wants to do, and optionally types of food." +
                        "Based off the given information, plan a travel itinerary for each day in the given time.  Start transportation after the first location. Do not start from a hotel unless told otherwise." +
                        "Format the response in the follow json format. Ignore backslashes in response. Apply spacing between words in a sentence: " +
                        "{\"itinerary\":{\"location\":\"STRING\",\"length\":123,\"interests\":\"STRING\",\"day_plan\":{\"day\":123,\"events\":123,\"plans\":[{\"address\":\"STRING\",\"name\":\"STRING\",\"description\":\"STRING\",\"keywords\":\"STRING\",\"type\":\"STRING\"},{\"address\":\"STRING\",\"name\":\"STRING\",\"description\":\"STRING\",\"keywords\":\"STRING\",\"type\":\"STRING\"}],\"transportation\":[{\"starting\":\"STRING\",\"starting_address\":\"STRING\",\"ending\":\"STRING\",\"ending_address\":\"STRING\",\"type_of_transport\":\"STRING\",\"commute_duration\":123,\"directions\":\"STRING\"},{\"starting\":\"STRING\",\"starting_address\":\"STRING\",\"ending\":\"STRING\",\"ending_address\":\"STRING\",\"type_of_transport\":\"STRING\",\"commute_duration\":123,\"directions\":\"STRING\"}]}}}" +
                        "If you are unable to find something, put not_found as the description." +
                        "Do not return anything but valid JSON. Validate JSON before returning."
            ),
            GptMessageSend(
                role = "user",
                content =
                "I am planning a 1 day trip to Seoul, South Korea. I want to see some modern Korean culture. I prefer walking or using public transportation. If needed, I can take a taxi."
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"itinerary\":{\"location\":\"Seoul, South Korea\",\"length\":1,\"interests\":\"Modern Korean Culture\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"55,Hangang-daero23-gil,Yongsan-gu,Seoul\",\"name\":\"National Museum of Korea\",\"description\":\"The National Museum of Korea is the flagship museum of Korean history and art in South Korea and is the cultural organization that represents Korea.\",\"keywords\":\"Museum,KoreanCulture,History,Art\",\"type\":\"Sightseeing\"},{\"address\":\"61, Cheongdam-dong,Gangnam-gu,Seoul\",\"name\":\"Gangnam Style Street\",\"description\":\"Gangnam Style Street is a trendy and fashionable street that has become famous for its upscale shopping and modern architecture.\",\"keywords\":\"Shopping, Architecture, Korean Culture\",\"type\":\"Sightseeing\"}],\"transportation\":[{\"starting\":\"National Museum of Korea\",\"starting_address\":\"55, Hangang-daero 23-gil,Yongsan-gu, Seoul\",\"ending\":\"Gangnam Style Street\",\"ending_address\":\"61, Cheongdam-dong, Gangnam-gu, Seoul\",\"type_of_transport\":\"Metro\",\"commute_duration\":40,\"directions\":\"Take the Line 4 from Ichon Station(National Museum of Korea) to Dongjak Station, then transfer to Line 3 and get off at Apgujeong Station. Gangnam Style Street is a 10-minutewalkfromthestation.\"},{\"starting\":\"GangnamStyleStreet\",\"starting_address\":\"61, Cheongdam-dong, Gangnam-gu, Seoul\",\"ending\":\"Seoul Station\",\"ending_address\":\"Seoul Station,405 Hangang-daero,Yeongdeungpo-gu,Seoul\",\"type_of_transport\":\"Taxi\",\"commute_duration\":25,\"directions\":\"Hail a taxi from Gangnam Style Street and ask the driver to take you to Seoul Station.\"}]}}}"
            ),
            GptMessageSend(
                role = "user",
                content = "I have 1 day to travel in Paris, France. I want to see historical sites and a more pop culture neighborhood."
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"itinerary\":{\"location\":\"Paris, France\",\"length\":1,\"interests\":\"Pop Culture Neighborhood with Good Street Food\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"Rue Crémieux,75012 Paris, France\",\"name\":\"Rue Crémieux\",\"description\":\"Colorful pedestrian street with pastel-colored houses and cafes.\",\"keywords\":\"Pop Culture, Photography, Instagram, Cafes.\",\"type\":\"Sightseeing\"},{\"address\":\"Marché des Enfants Rouges, 39 Rue de Bretagne, 75003 Paris, France.\",\"name\":\"Marché des Enfants Rouges.\",\"description\":\"Historic covered market with international street food vendors and seating area.\",\"keywords\":\"Street Food, International Cuisine, Market, Pop Culture.\",\"type\":\"Food\"}],\"transportation\":[{\"starting\":\"Rue Crémieux\",\"starting_address\":\"Rue Crémieux, 75012 Paris, France.\",\"ending\":\"Marché des Enfants Rouges.\",\"ending_address\":\"39 Rue de Bretagne, 75003 Paris, France\",\"type_of_transport\":\"Walking\",\"commute_duration\":20,\"directions\":\"Head north on Rue Crémieux toward Rue de Lyon, turn right onto Boulevard de la Bastille, turn left onto Rue de Turenne, turn right onto Rue Charlot, turn left onto Rue de Bretagne, and the destination will be on the left.\"},{\"starting\":\"Marché des Enfants Rouges.\",\"starting_address\":\"39 Rue de Bretagne, 75003 Paris, France.\",\"ending\":\"Rue Crémieux\",\"ending_address\":\"Rue Crémieux, 75012 Paris, France.\",\"type_of_transport\":\"Taxi\",\"commute_duration\":15,\"directions\":\"To take a taxi from Marché des Enfants Rouges to Rue Crémieux, which will take approximately 15 minutes depending on traffic.\"}]}}}"
            ),
            GptMessageSend(
                role = "user",
                content = "I have 1 day to travel in Danang, Vietnam. I am interested in local art and the nightlife. Do not include any transportation instructions."
            ),
            GptMessageSend(
                role = "assistant",
                content =
                "{\"itinerary\":{\"location\":\"Danang, Vietnam\",\"length\":1,\"interests\":\"local art and nightlife\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"22 Bach Dang Street, Hai Chau District, Danang, Vietnam\",\"name\":\"Danang Fine Arts Museum\",\"description\":\"A museum showcasing the art of the region including paintings, sculptures, and antiques.\",\"keywords\":\"local art,museum\",\"type\":\"sightseeing\"},{\"address\":\"88 Bach Dang Street, Hai Chau District, Danang, Vietnam\",\"name\":\"Sky36 Bar\",\"description\":\"A rooftop bar located on the 36th floor of Novotel Danang Premier Han River. It offers stunning views of the city and serves a wide range of drinks.\",\"keywords\":\"nightlife, rooftop bar\",\"type\":\"nightlife\"}],\"transportation\":[]}}}"
            ),
            GptMessageSend(
                role = "user",
                content = "I have 1 day to travel in Tokyo, Japan. I am interested in local food and shopping. Do not include any transportation instructions."
            ),
        )
    }
}