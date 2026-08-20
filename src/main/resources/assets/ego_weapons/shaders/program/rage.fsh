#version 120

uniform sampler2D DiffuseSampler;
uniform float GameTime;
uniform vec2 OutSize;

varying vec2 texCoord;

float rand(vec2 co) {
    return fract(sin(dot(co.xy, vec2(12.9898,78.233))) * 43758.5453);
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

    vec2 centered = uv - 0.5;
    float dist = length(centered);
    dist = max(0, dist-0.25);

    // slight distortion/wobble
    float distortion = sin(uv.y * 12.0 + GameTime * 2.5) * 0.01;
    uv.x += distortion * dist;

    vec4 color = texture2D(DiffuseSampler, uv);

    // distance from center

    // vignette mask
    float vignette = smoothstep(0.0, 0.65, dist);

    // animated edge noise
    float noiseFX = rand(uv * OutSize.xy + GameTime) * 0.08;

    float n3 = noise(uv * 20.0 - GameTime * 0.4);

    // red glow
    vec3 redTint = vec3(0.5 + (n3 / 2), 0.0, 0.0);


    // apply effect only near edges
    color.rgb += redTint * vignette * (0.45 + noiseFX);

    // slight darkening for dramatic effect
    color.rgb *= 1.0 - (vignette * 1);

    gl_FragColor = color;
}