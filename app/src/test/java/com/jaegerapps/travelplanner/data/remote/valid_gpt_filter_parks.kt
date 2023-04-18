package com.jaegerapps.travelplanner.data.remote

val valid_gpt_filter_parks = """
    {"id":"chatcmpl-72Su73n5v82zIlvvkaWJg3wlC3jtz","object":"chat.completion","created":1680822119,"model":"gpt-3.5-turbo-0301","usage":{"prompt_tokens":828,"completion_tokens":36,"total_tokens":864},"choices":[{"message":{"role":"assistant","content":"{\"places\":[\"Namsan Seoul Tower\",\"Itaewon Children’s Park\",\"Tapgol Park\",\"Changdeok-gung Palace Park\",\"Sajik Park\"]}"},"finish_reason":"stop","index":0}]}
""".trimIndent()

val valid_gpt_filter_street_food = """
    {"id":"chatcmpl-72Su7JHn0UKe4lyVpxS3rlv9OdyXi","object":"chat.completion","created":1680822119,"model":"gpt-3.5-turbo-0301","usage":{"prompt_tokens":768,"completion_tokens":36,"total_tokens":804},"choices":[{"message":{"role":"assistant","content":"{\"places\":[\"Gwangjang Market Food Alley\",\"Myeongdong street scallop\",\"Namdaemun Market Food Street\",\"Hongdae Food Street\",\"Street Churros\"]}"},"finish_reason":"stop","index":0}]}
""".trimIndent()