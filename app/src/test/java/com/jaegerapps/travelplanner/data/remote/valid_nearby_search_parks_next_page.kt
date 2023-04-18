package com.jaegerapps.travelplanner.data.remote

val valid_nearby_search_parks_next_page = """
    {
        "html_attributions": [],
        "results": [
            {
                "business_status": "OPERATIONAL",
                "formatted_address": "Seoul, South Korea",
                "geometry": {
                    "location": {
                        "lat": 37.550995,
                        "lng": 126.991111
                    },
                    "viewport": {
                        "northeast": {
                            "lat": 37.5523439802915,
                            "lng": 126.9924599802915
                        },
                        "southwest": {
                            "lat": 37.5496460197085,
                            "lng": 126.9897620197085
                        }
                    }
                },
                "icon": "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/bus-71.png",
                "icon_background_color": "#10BDFF",
                "icon_mask_base_uri": "https://maps.gstatic.com/mapfiles/place_api/icons/v2/bus_share_taxi_pinlet",
                "name": "Namsan Seoul Tower",
                "photos": [
                    {
                        "height": 4080,
                        "html_attributions": [
                            "<a href=\"https://maps.google.com/maps/contrib/103393971934047436385\">Saurabh Shringarpure</a>"
                        ],
                        "photo_reference": "AUjq9jkXptFoytqO6TWW5hXd_CiiQJUPB6xnWOfNhTZszSAJ6xTCiGQKEXH5jgYIlnhXXDd04FdS3xXCvygTr6XjwtpGodvsXi7oKJpqnR7yyvLKSYKMixVJCqRQY4yLahMwyog1v8SDVCQ5m8z67UtJ0GAEYn-Ve6sXWspuwHuo-XAmKdbr",
                        "width": 3072
                    }
                ],
                "place_id": "ChIJayceclaifDURZVXO_FfROGY",
                "plus_code": {
                    "compound_code": "HX2R+QP3 Seoul, South Korea",
                    "global_code": "8Q98HX2R+QP3"
                },
                "rating": 4.3,
                "reference": "ChIJayceclaifDURZVXO_FfROGY",
                "types": [
                    "bus_station",
                    "transit_station",
                    "point_of_interest",
                    "establishment"
                ],
                "user_ratings_total": 3
            }
        ],
        "status": "OK"
    }
""".trimIndent()