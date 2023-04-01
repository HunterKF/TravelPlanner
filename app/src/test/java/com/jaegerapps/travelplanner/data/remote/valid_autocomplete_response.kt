package com.jaegerapps.travelplanner.data.remote

val valid_autocomplete_response = """
    {
       "predictions" : [
          {
             "description" : "Paris, France",
             "matched_substrings" : [
                {
                   "length" : 4,
                   "offset" : 0
                }
             ],
             "place_id" : "ChIJD7fiBh9u5kcRYJSMaMOCCwQ",
             "reference" : "ChIJD7fiBh9u5kcRYJSMaMOCCwQ",
             "structured_formatting" : {
                "main_text" : "Paris",
                "main_text_matched_substrings" : [
                   {
                      "length" : 4,
                      "offset" : 0
                   }
                ],
                "secondary_text" : "France"
             },
             "terms" : [
                {
                   "offset" : 0,
                   "value" : "Paris"
                },
                {
                   "offset" : 7,
                   "value" : "France"
                }
             ],
             "types" : [ "locality", "political", "geocode" ]
          },
          {
             "description" : "Pari Chowk, NRI City, Omega II, Noida, Uttar Pradesh, India",
             "matched_substrings" : [
                {
                   "length" : 4,
                   "offset" : 0
                }
             ],
             "place_id" : "EjtQYXJpIENob3drLCBOUkkgQ2l0eSwgT21lZ2EgSUksIE5vaWRhLCBVdHRhciBQcmFkZXNoLCBJbmRpYSIuKiwKFAoSCSvbxs7VwQw5EaQKqM6g9V1WEhQKEgnPC8bw08EMORFiiyvvNAwEXg",
             "reference" : "EjtQYXJpIENob3drLCBOUkkgQ2l0eSwgT21lZ2EgSUksIE5vaWRhLCBVdHRhciBQcmFkZXNoLCBJbmRpYSIuKiwKFAoSCSvbxs7VwQw5EaQKqM6g9V1WEhQKEgnPC8bw08EMORFiiyvvNAwEXg",
             "structured_formatting" : {
                "main_text" : "Pari Chowk",
                "main_text_matched_substrings" : [
                   {
                      "length" : 4,
                      "offset" : 0
                   }
                ],
                "secondary_text" : "NRI City, Omega II, Noida, Uttar Pradesh, India"
             },
             "terms" : [
                {
                   "offset" : 0,
                   "value" : "Pari Chowk"
                },
                {
                   "offset" : 12,
                   "value" : "NRI City"
                },
                {
                   "offset" : 22,
                   "value" : "Omega II"
                },
                {
                   "offset" : 32,
                   "value" : "Noida"
                },
                {
                   "offset" : 39,
                   "value" : "Uttar Pradesh"
                },
                {
                   "offset" : 54,
                   "value" : "India"
                }
             ],
             "types" : [ "route", "geocode" ]
          },
          {
             "description" : "Paris, TX, USA",
             "matched_substrings" : [
                {
                   "length" : 4,
                   "offset" : 0
                }
             ],
             "place_id" : "ChIJmysnFgZYSoYRSfPTL2YJuck",
             "reference" : "ChIJmysnFgZYSoYRSfPTL2YJuck",
             "structured_formatting" : {
                "main_text" : "Paris",
                "main_text_matched_substrings" : [
                   {
                      "length" : 4,
                      "offset" : 0
                   }
                ],
                "secondary_text" : "TX, USA"
             },
             "terms" : [
                {
                   "offset" : 0,
                   "value" : "Paris"
                },
                {
                   "offset" : 7,
                   "value" : "TX"
                },
                {
                   "offset" : 11,
                   "value" : "USA"
                }
             ],
             "types" : [ "locality", "political", "geocode" ]
          },
          {
             "description" : "Parigi, Province of Parma, Italy",
             "matched_substrings" : [
                {
                   "length" : 4,
                   "offset" : 0
                }
             ],
             "place_id" : "ChIJlfM5m4FygEcRZGwT9ziKmtE",
             "reference" : "ChIJlfM5m4FygEcRZGwT9ziKmtE",
             "structured_formatting" : {
                "main_text" : "Parigi",
                "main_text_matched_substrings" : [
                   {
                      "length" : 4,
                      "offset" : 0
                   }
                ],
                "secondary_text" : "Province of Parma, Italy"
             },
             "terms" : [
                {
                   "offset" : 0,
                   "value" : "Parigi"
                },
                {
                   "offset" : 8,
                   "value" : "Province of Parma"
                },
                {
                   "offset" : 27,
                   "value" : "Italy"
                }
             ],
             "types" : [ "locality", "political", "geocode" ]
          },
          {
             "description" : "Paris, Brant, ON, Canada",
             "matched_substrings" : [
                {
                   "length" : 4,
                   "offset" : 0
                }
             ],
             "place_id" : "ChIJsamfQbVtLIgR-X18G75Hyi0",
             "reference" : "ChIJsamfQbVtLIgR-X18G75Hyi0",
             "structured_formatting" : {
                "main_text" : "Paris",
                "main_text_matched_substrings" : [
                   {
                      "length" : 4,
                      "offset" : 0
                   }
                ],
                "secondary_text" : "Brant, ON, Canada"
             },
             "terms" : [
                {
                   "offset" : 0,
                   "value" : "Paris"
                },
                {
                   "offset" : 7,
                   "value" : "Brant"
                },
                {
                   "offset" : 14,
                   "value" : "ON"
                },
                {
                   "offset" : 18,
                   "value" : "Canada"
                }
             ],
             "types" : [ "neighborhood", "political", "geocode" ]
          }
       ],
       "status" : "OK"
    }
""".trimIndent()