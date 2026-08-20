#version 120

uniform sampler2D DiffuseSampler;
uniform float GameTime;

/*
    MemoryLoss:
    0.0 = normal
    1.0 = severe memory degradation
*/
uniform float MemoryLoss;

varying vec2 texCoord;

float rand(vec2 co) {
    return fract(
        sin(dot(co.xy, vec2(12.9898, 78.233)))
        * 43758.5453
    );
}

float hash(vec2 p) {
    return fract(
        sin(dot(p, vec2(127.1, 311.7))) *
        43758.5453123
    );
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);

    float a = hash(i);
    float b = hash(i + vec2(1.0, 0.0));
    float c = hash(i + vec2(0.0, 1.0));
    float d = hash(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x)
    + (c - a) * u.y * (1.0 - u.x)
    + (d - b) * u.x * u.y;
}

void main() {

    vec2 uv = texCoord;

    vec4 color = texture2D(DiffuseSampler, uv);

    /*
        =========================
        DESATURATION
        =========================
    */

    float gray =
    dot(color.rgb, vec3(0.299, 0.587, 0.114));

    color.rgb = mix(
        color.rgb,
        vec3(gray),
        MemoryLoss * 0.9
    );

/*
        =========================
        DARK VIGNETTE
        =========================
    */

    vec2 centered = uv - 0.5;

    float dist = length(centered);

    float vignette =
    smoothstep(0.25, 0.7, dist);


    float n3 = noise((uv + vec2(sin(uv.y * 700) * 0.007, 0)) * 20.0);
    float n4 = noise((uv + vec2( 0, sin(uv.x * 700) * 0.007)) * 40.0);

    n3 = (n3 + n4) / 2;

    bool whitePixel = false;

    if (n3 + (0.25 - vignette / 2) < (min(0.6,MemoryLoss * 0.9))) {
        color.rgb = vec3(1.0,1.0,1.0);
        whitePixel = true;
    }



/*
        subtle vertical distortion
    */
    float wobble =
    sin(uv.y * 30.0 + GameTime * 2.0)
    * 0.002
    * MemoryLoss;

    uv.x += wobble;

/*
        blend scratches into image
    */
    // color.rgb += scratches * MemoryLoss;

/*
        =========================
        FAINT STATIC GRAIN
        =========================
    */

    float grain =
    rand(uv + GameTime)
    * 0.3
    * MemoryLoss;

    // On white pixels noise is inverted.
    if (whitePixel) {
        grain *= -1 - n3;
    }


    color.rgb *=
    1.0 - (vignette * min(MemoryLoss,0.8f) * 1.2);

    color.rgb += grain;



    gl_FragColor = color;
}