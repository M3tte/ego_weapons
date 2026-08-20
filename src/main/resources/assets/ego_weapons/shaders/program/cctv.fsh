#version 120

uniform sampler2D DiffuseSampler;
uniform float GameTime;
uniform vec2 OutSize;

varying vec2 texCoord;

/* pseudo-random noise */
float rand(vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main() {
    vec2 uv = texCoord;

    // slight jitter (old CCTV instability)
    float jitter = (rand(vec2(GameTime, uv.y)) - 0.5) * 0.002;
    uv.x += jitter;

    vec4 color = texture2D(DiffuseSampler, uv);

    // yellow-green CCTV tint
    vec3 tint = vec3(1.15, 1.05, 0.6);
    color.rgb *= tint;

    // scanlines
    float scan = sin(uv.y * OutSize.y * 1.2 + GameTime * 20.0) * 0.04;
    color.rgb -= scan;

    // vignette
    vec2 center = uv - 0.5;
    float vignette = 1.0 - dot(center, center) * 1.2;
    color.rgb *= vignette;

    // noise/static
    float noise = rand(uv * OutSize + GameTime) * 0.05;
    color.rgb += noise;

    gl_FragColor = color;
}