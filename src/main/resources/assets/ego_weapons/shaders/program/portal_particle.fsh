#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D StarSampler;

uniform float GameTime;
uniform float WarpStrength;
uniform float StarIntensity;

varying vec2 texCoord;
varying vec3 viewPos;
varying vec4 vertexColor;

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

    // Particle mask
    vec4 particle =
    texture2D(DiffuseSampler, texCoord);

    float mask = smoothstep(0.05, 0.4, particle.a);

    // Camera-relative warp
    vec3 viewDir = normalize(viewPos);

    vec2 cameraWarp =
    viewDir.xy / (abs(viewDir.z) + 0.15);

    cameraWarp *= WarpStrength;

    // =====================================================
    // BACKGROUND UV
    // =====================================================

    vec2 bgUV =
    cameraWarp * 0.8;

    // Add slow drift
    bgUV += vec2(GameTime * 0.01, GameTime * 0.006);

    // =====================================================
    // NOISE (camera-relative)
    // =====================================================

    float n =
    noise(bgUV * 30.0 + GameTime * 0.2);

    float n2 =
    noise(bgUV * 50.0 - GameTime * 0.15);

    float n3 =
    noise(bgUV * 50.0 - GameTime * 0.4);


    float noiseValue =
    (n * 0.7 + n2 * 0.3);

    // Convert to -1..1
    noiseValue = noiseValue * 2.0 - 1.0;



    // =====================================================
    // BASE GRADIENT BACKGROUND
    // =====================================================

    vec3 deepBlue = vec3(0.03, 0.02, 0.12);
    vec3 purple   = vec3(0.40, 0.08, 0.65);
    vec3 cyan     = vec3(0.10, 0.25, 0.50);

    float gradient = bgUV.y * 0.5 + 0.5;

    vec3 background =
    mix(deepBlue, purple, gradient);

    background += cyan *
    pow(max(0.0, 1.0 - abs(bgUV.x)), 2.0)
    * 0.25;

    // =====================================================
    // APPLY NOISE TO BACKGROUND ONLY
    // =====================================================

    background += noiseValue * 0.08;   // brightness flicker
    background.rg += noiseValue * 0.2; // slight color shift

    background.rgb -= n3 * 0.3;


    // subtle “energy turbulence”
    bgUV += noiseValue * 0.06;

    // =====================================================
    // STARS (UNCHANGED LOGIC)
    // =====================================================

    vec2 starUV1 = cameraWarp * 6.0 + vec2(GameTime * 0.008, GameTime * 0.004);
    vec2 starUV2 = cameraWarp * 12.0 - vec2(GameTime * 0.012, -GameTime * 0.006);
    vec2 starUV3 = cameraWarp * 20.0 + vec2(GameTime * 0.018, -GameTime * 0.010);

    starUV1 += texCoord * 0.15;
    starUV2 += texCoord * 0.10;
    starUV3 += texCoord * 0.05;

    vec3 stars =
    texture2D(StarSampler, starUV1).rgb * 0.55 +
    texture2D(StarSampler, starUV2).rgb * 0.35 +
    texture2D(StarSampler, starUV3).rgb * 0.25;

    stars *= StarIntensity;

    // =====================================================
    // FINAL
    // =====================================================

    vec3 finalColor =
    background + stars;

    finalColor *= 1.25;

    finalColor *= vertexColor.rgb;

    float alpha = mask * vertexColor.a;

    gl_FragColor = vec4(finalColor, alpha);
}