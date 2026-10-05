#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D MaskSampler;

varying vec2 texCoord;
uniform vec2 OutSize;
uniform vec2 BlurSize;


void main() {
    vec4 base = texture2D(MaskSampler, texCoord);

    // Horizontal Gaussian-ish blur.
    vec3 bloom = vec3(0.0);

    vec2 pixelOffset = 1 / BlurSize;

    bloom += texture2D(MaskSampler, texCoord).rgb * 1;

    for (float vert = -2; vert < 2; vert++) {
        float changeFactor = 1 / (27 - abs(vert));

        for (float i = -15 + abs(vert); i < 15 - abs(vert); i++) {
            bloom += 0.03 * texture2D(MaskSampler, texCoord + vec2(i * pixelOffset.x , vert * pixelOffset.y)).rgb * (1 - changeFactor * abs(i));

            if (bloom.r > 1.1 || bloom.g > 1.1 || bloom.b > 1.1) {
                break;
            }



        }
    }



    // Bloom strength.

    // Add bloom on top of the original scene.
    gl_FragColor = vec4(bloom, min(bloom,1));
}