#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D MaskSampler;

varying vec2 texCoord;
uniform vec2 OutSize;
uniform vec2 BlurSize;

uniform float approxBase = 0.6;
uniform float approxMin = 0.03f;
uniform float maxHor = 20;
uniform float maxVert = 2;
uniform float vertFactor = 3;




void main() {
    vec4 base = texture2D(MaskSampler, texCoord);

    // Horizontal Gaussian-ish blur.
    vec3 bloom = vec3(0.0);

    float alph = 0;

    vec2 pixelOffset = 1 / OutSize;



    // bloom += texture2D(MaskSampler, texCoord).rgb * 1;

    for (float vert = -maxVert; vert < maxVert; vert++) {

        for (float i = -maxHor + abs(vert * vertFactor); i < maxHor - abs(vert * vertFactor); i++) {

            float approximationFactor = approxBase / (1 + abs(i * 0.9f) + abs(vert * vertFactor)) - approxMin;

            vec3 val = approximationFactor * texture2D(MaskSampler, texCoord + vec2(i * pixelOffset.x , vert * pixelOffset.y)).rgb;
            bloom += val;
            float avg = max(max(val.r, val.g),val.b);
            alph += avg;
            if (avg > 1) {
                break;
            }

        }
    }
    // Add bloom on top of the original scene.
    gl_FragColor = vec4(bloom.rgb, min(alph,1));
}