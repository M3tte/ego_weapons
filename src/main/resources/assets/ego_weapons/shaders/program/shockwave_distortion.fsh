#version 120

uniform sampler2D DiffuseSampler;
uniform float GameTime;
uniform vec2 OutSize;

uniform float EffectAmpl;

varying vec2 texCoord;

float random(vec2 p)
{
    return fract(
        sin(dot(p, vec2(127.1, 311.7))) * 43758.5453123
    );
}

// ---------------------------------------------------------
// Main
// ---------------------------------------------------------

void main()
{
    vec2 uv = texCoord;

    // Center of the screen
    vec2 center = uv - vec2(0.5);

    float distanceFromCenter = length(center);

    // -----------------------------------------------------
    // ROAR PULSE
    //
    // Creates a strong "impact" feeling when the beast roars.
    // -----------------------------------------------------

    float roarWave = (sin(GameTime * 14) / 2 + 2) * EffectAmpl;

    // Make the distortion strongest around the middle
    // and weaker toward the very center.
    float wave =
    (sin(distanceFromCenter * 18.0 - GameTime * 26.0) / 2 + 2) * EffectAmpl;

    wave *= smoothstep(0.05, 0.75, distanceFromCenter);

    // TUNNEL VISION
    float tunnel =
    smoothstep(0.15, 0.85f, distanceFromCenter);

    float tunnelStrength =
    tunnel * (0.05 + roarWave * 0.006) * EffectAmpl * 2;

    // Radial distortion
    vec2 direction =
    normalize(center + vec2(0.00001));

    uv -= direction * tunnelStrength;

    // ROAR DISTORTION
    float distortion =
    wave *
    0.055 *
    smoothstep(0.1, 0.9, distanceFromCenter) * EffectAmpl;

    uv += direction * distortion;

    // CHROMATIC ABERRATION
    vec2 chromaticDirection =
    normalize(center + vec2(0.00001));

    float chromaticAmount =
    distanceFromCenter *
    (0.003 + abs(roarWave) * 0.001)
    * EffectAmpl;

    vec2 chromaticOffset =
    chromaticDirection * chromaticAmount;

    float red =
    texture2D(
        DiffuseSampler,
        uv + chromaticOffset
    ).r;

    float green =
    texture2D(
        DiffuseSampler,
        uv
    ).g;

    float blue =
    texture2D(
        DiffuseSampler,
        uv - chromaticOffset
    ).b;

    vec3 color =
    vec3(red, green, blue);

    // Darkening Pulse
    float flash =
    max(0.0, sin(GameTime * 24.0) - 0.5);

    flash =
    pow(flash, 8.0);

    color -=
    vec3(0.1, 0.1, 0.1) *
    flash
    * EffectAmpl;

    // Desaturation
    float gray =
    dot(
        color,
        vec3(0.299, 0.587, 0.114)
    );

    color =
    mix(
        vec3(gray),
        color,
        max(0,1-(0.01 + 0.15 * smoothstep(0.15, 0.8, distanceFromCenter) * EffectAmpl))
    );


    // Pulsing Vignette
    float vignette =
    smoothstep(
        0.30,
        0.78,
        distanceFromCenter
    )
    * EffectAmpl;

    // Pulse the darkness with the roar
    float vignetteStrength =
    0.45 +
    abs(roarWave / 3) * 0.15;

    color *=
    1.0 -
    vignette *
    vignetteStrength;

    // PERIPHERAL DISTORTION
    float peripheral =
    smoothstep(
        0.35,
        0.85,
        distanceFromCenter
    )
    * EffectAmpl;

    color *=
    1.0 -
    peripheral * 0.10;


    // SUBTLE GRAIN
    float noise =
    random(
        texCoord * 400.0 +
        GameTime
    ) - 0.5;

    color +=
    noise * 0.012 * EffectAmpl;

    // FINAL COLOR
    color =
    clamp(
        color,
        0.0,
        1.0
    );

    gl_FragColor =
    vec4(color, 1.0);
}