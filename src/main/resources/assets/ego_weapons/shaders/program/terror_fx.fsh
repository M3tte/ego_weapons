#version 120

uniform sampler2D DiffuseSampler;
uniform float GameTime;
uniform vec2 OutSize;

uniform float EffectAmpl;

varying vec2 texCoord;

float rand(vec2 co)
{
    return fract(
        sin(dot(co, vec2(12.9898, 78.233))) *
        43758.5453
    );
}
const float PI = 3.14159265;
void main()
{
    vec2 uv = texCoord;

    // Zoom in Effect

    float breathing = sin(GameTime * 3);
    float zoom = 1.0 + breathing * 0.01 * EffectAmpl;

    uv = (uv - 0.5) / zoom + 0.5;


    // Chrom. Abberation

    vec2 center = uv - 0.5;
    float dist = length(center);

    vec2 direction = normalize(center + vec2(0.0001));
    vec2 aberration = direction * dist * 0.014 * EffectAmpl * (((breathing + 0.8) / 3) + 0.5f) ;

    float r = texture2D(
        DiffuseSampler,
        uv + aberration
    ).r;

    float g = texture2D(
        DiffuseSampler,
        uv
    ).g;

    float b = texture2D(
        DiffuseSampler,
        uv - aberration
    ).b;

    vec3 color = vec3(r, g, b);

    // Desaturation Effect

    float gray = dot(
        color,
        vec3(0.299, 0.587, 0.114)
    );

    color = mix(
        vec3(gray),
        color,
        1 - 0.65 * EffectAmpl
    );

    // CONTRAST

    color = (color - 0.5) * (1 + 0.12 * EffectAmpl) + 0.5;

    // Heartbeat / Pulsing effect, might change


    float heartbeatPhase = mod(GameTime * 120 / 60.0f, 5);

    float beat1 = max(0.0f, sin(heartbeatPhase * PI * 8.0f));
    float beat2 = max(0.0f, sin(heartbeatPhase - 0.18f) * PI * 10.0f) * 0.5f;
    float heartbeat = beat1 + beat2;

    color *= 1.0 - heartbeat * 0.01  * EffectAmpl;

    // Tunnel Vision Effect

    float vignette = smoothstep(
        0.20,
        0.82,
        dist * EffectAmpl
    );

    color *= mix(
        1.0,
        0.35,
        vignette
    );


    // Grain

    float grain = rand(
        texCoord * GameTime * 200.0
    ) - 0.5;

    color += grain * 0.02 * EffectAmpl;

    // =====================================================
    // OUTPUT
    // =====================================================

    gl_FragColor = vec4(color, 1.0);
}